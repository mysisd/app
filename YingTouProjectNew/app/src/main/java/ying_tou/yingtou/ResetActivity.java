package ying_tou.yingtou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ResetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtPhone, mEtCode, mEtPwd, mEtPwdAgain;
    private String randomNum;
    private String mCode;
    private Sms sms;
    private Button mBtnCode;
    private TimeCount timeCount;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initUI();
        sms = new Sms();
        //构造CountDownTimer对象(60秒)
        timeCount = new TimeCount(60000, 1000, mBtnCode);
        randomNum = (int) ((Math.random() * 9 + 1) * 100000) + "";//获取0~9的随机6位数

    }

    private void initUI() {
        mEtPhone = (EditText) findViewById(R.id.et_reset_phone);
        mEtCode = (EditText) findViewById(R.id.et_reset_code);
        mEtPwd = (EditText) findViewById(R.id.et_reset_pwd);
        mEtPwdAgain = (EditText) findViewById(R.id.et_reset_pwd_again);
        findViewById(R.id.btn_reset_confirm).setOnClickListener(this);
        findViewById(R.id.btn_reset_cancel).setOnClickListener(this);
        mBtnCode = (Button) findViewById(R.id.btn_reset_code);
        mBtnCode.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_code:
                final String phone = mEtPhone.getText().toString().trim();
                if (!phoneFormat()) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.URL_REGISTER)
                            .build();
                    RetrofitServices services = retrofit.create(RetrofitServices.class);
                    Call<ResponseBody> call = services.getLessUrl("has_phone?phone=" + phone);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String json = response.body().string();
                                Gson gson = new Gson();
                                DataBaseField bean = gson.fromJson(json, DataBaseField.class);
                                String exist = bean.getExist();
                                //0:已注册;1:没注册
                                if (exist.equals("1")) {
                                    Toast.makeText(ResetActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                                } else {
                                    timeCount.start();//开始计时
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
                break;
            case R.id.btn_reset_confirm://确定
                reset();
                break;
            case R.id.btn_reset_cancel://取消
                finish();
                break;
        }
    }

    /**
     * 确定
     */
    private void reset() {
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
            if (!pwd.equals(pwdAgain)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Constant.URL_LOGIN)
                    .build();
            RetrofitServices services = retrofit.create(RetrofitServices.class);
            Call<ResponseBody> call = services.getLessUrl("findpwds?phone=" + phone + "&password=" + pwd);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        DataBaseField bean = gson.fromJson(json, DataBaseField.class);
                        String reset = bean.getReset();
                        if ("success".equals(reset)) {
                            PublicSetting.autoDelay("重置成功", ResetActivity.this);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    /**
     * 判空
     */
    public boolean isEmpty() {
        if (mEtCode.getText().toString().trim().equals("")) {
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
}
