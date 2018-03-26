package ying_tou.yingtou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import Bean.DataBaseField;
import Interface.RetrofitServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Utils.Constant;
import Utils.PublicSetting;
import Utils.Sms;
import Utils.SmsUser;
import Utils.TimeCount;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox mCbAgree;
    private EditText mEtPhone, mEtCode, mEtPwd, mEtPwdAgain;
    private String mCode;
    private Sms sms;
    private String randomNum;
    private Button mBtnCode;
    private TimeCount timeCount;//倒计时.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        randomNum = (int) ((Math.random() * 9 + 1) * 100000) + "";//获取0~9的随机6位数
        //构造CountDownTimer对象(60秒)
        timeCount = new TimeCount(60000, 1000, mBtnCode);
        sms = new Sms();
    }

    private void initUI() {
        mEtPhone = (EditText) findViewById(R.id.et_register_phone);//手机号
        mEtCode = (EditText) findViewById(R.id.et_register_code);//验证码
        mEtPwd = (EditText) findViewById(R.id.et_register_pwd);//密码
        mEtPwdAgain = (EditText) findViewById(R.id.et_register_pwd_again);//确认密码
        mCbAgree = (CheckBox) findViewById(R.id.cb_register_agree);//注册协议
        mBtnCode = (Button) findViewById(R.id.btn_register_code);
        mBtnCode.setOnClickListener(this);
        findViewById(R.id.tv_register_agreement).setOnClickListener(this);
        findViewById(R.id.btn_register_confirm).setOnClickListener(this);
        findViewById(R.id.btn_register_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_agreement://注册协议
                Intent intent = new Intent(this, RegisterAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register_code://获取验证码
                getCode();
                break;
            case R.id.btn_register_confirm://确认注册
                registered();
                break;
            case R.id.btn_register_cancel:
                finish();
                break;
        }
    }
    /**
     * 获取短信验证码
     */
    private void getCode() {

        if (!phoneFormat()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.URL_REGISTER)
                    .build();
            //http://xmyttz.com/rx/register/has_phone?phone=18559228338 还没修改
            RetrofitServices services = retrofit.create(RetrofitServices.class);
            Call<ResponseBody> call = services.getLessUrl("has_phone?phone=" + mEtPhone.getText().toString().trim());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        DataBaseField bean = gson.fromJson(json, DataBaseField.class);
                        String exist = bean.getExist();
                        //0:已注册;1:没注册
                        if (exist.equals("0")) {
                            Toast.makeText(RegisterActivity.this, "用户已存在,不能重复注册", Toast.LENGTH_SHORT).show();
                        } else {
                            timeCount.start();//开始计时
                            final String phone = mEtPhone.getText().toString().trim();
                            SmsUser smsUser = new SmsUser("Tom", randomNum);
                            Log.e("randomNum", randomNum);
                            mCode = gson.toJson(smsUser);
                            //启动新线程 用来发送短信
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        sms.getSms(phone, mCode);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    }

    /**
     * 确认注册
     */
    private void registered() {
        if (isEmpty()) {
            String phone = mEtPhone.getText().toString().trim();
            String code = mEtCode.getText().toString().trim();
            String pwd = mEtPwd.getText().toString().trim();
            String pwdAgain = mEtPwdAgain.getText().toString().trim();

            if (phoneFormat()) return;
            if (!code.equals(randomNum)) {
                Toast.makeText(this, "验证码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pwd.length() < 6 || pwd.length() >16) {
                Toast.makeText(this, "密码长度为6~16之间", Toast.LENGTH_SHORT).show();
                return;
            }
            //两次密码输入不一样
            if (!pwd.equals(pwdAgain)) {
                Toast.makeText(this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!mCbAgree.isChecked()) {
                Toast.makeText(this, "请先阅读注册协议", Toast.LENGTH_SHORT).show();
                return;
            }
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Constant.URL_REGISTER)
                    .build();
            RetrofitServices services = retrofit.create(RetrofitServices.class);
            //http://xmyttz.com/rx/register/registers?phone=18559228338
            Call<ResponseBody> call = services.getLessUrl("registers?phone=" + phone + "&password=" + pwd);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        DataBaseField bean = gson.fromJson(json, DataBaseField.class);
                        String register = bean.getRegister();
                        //返回success  error
                        if (register.equals("success")) {
                            //两秒后自动跳转，并弹出Toast
                            PublicSetting.autoDelay("注册成功", RegisterActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    /**
     * 判断手机号格式是否正确(格式:不能空,号码合法,手机号已注册)
     */
    private boolean phoneFormat() {
        String phone = mEtPhone.getText().toString().trim();
        if (phone.equals("")) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

        //检查输入的手机号是否合法
        if (!PublicSetting.isMobile(phone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    /**
     * 判断帐号或者密码是否为空
     */
    public boolean isEmpty() {
        if (mEtPhone.getText().toString().trim().equals("")) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mEtCode.getText().toString().trim().equals("")) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mEtPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mEtPwdAgain.getText().toString().trim().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}