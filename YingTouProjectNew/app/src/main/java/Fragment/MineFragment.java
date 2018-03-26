package Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ying_tou.yingtou.AboutUsActivity;
import ying_tou.yingtou.AccountActivity;
import ying_tou.yingtou.R;

public class MineFragment extends Fragment implements View.OnClickListener {

    private TextView mTvPhone;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mine, container, false);
        initUI(layout);
        getPhone();
        return layout;
    }

    private void initUI(View layout) {
        mTvPhone = (TextView) layout.findViewById(R.id.tv_mine_phone);
        layout.findViewById(R.id.tv_mine_account).setOnClickListener(this);
        layout.findViewById(R.id.tv_mine_customer_service).setOnClickListener(this);
        layout.findViewById(R.id.tv_mine_guide).setOnClickListener(this);
        layout.findViewById(R.id.tv_mine_about_us).setOnClickListener(this);
        layout.findViewById(R.id.btn_mine_exit).setOnClickListener(this);
    }

    /**
     * 获取登录界面传过来的手机号，并对其进行为*显示方式，例如 134****5678
     */
    private void getPhone() {
        Intent intent = getActivity().getIntent();
        //获取登录的手机号
        String phone = intent.getStringExtra("PHONE");
        //将手机号显示为中间四位是*
        String newPhone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        mTvPhone.setText(newPhone);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_mine_account:
                startActivity(new Intent(getActivity(), AccountActivity.class));
                break;
            case R.id.tv_mine_customer_service:
                initDialog();
                break;
            case R.id.tv_mine_guide:
                break;
            case R.id.tv_mine_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.btn_mine_exit:

                new AlertDialog.Builder(getActivity())
                        .setMessage("确定退出吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();


                break;
        }
    }

    /**
     * 设置“关于我们”的弹出窗口
     */
    private void initDialog() {
        //设置内容及标题居中
        TextView title = new TextView(getActivity());
        title.setText("400-850-1626");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);

        TextView msg = new TextView(getActivity());
        msg.setText("周一 早9：00 至 周五 晚6：00");
        msg.setPadding(10, 10, 10, 10);
        msg.setGravity(Gravity.CENTER);
        msg.setTextSize(18);
        msg.setTextColor(Color.BLACK);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(title);
        builder.setView(msg);

        builder.setPositiveButton("拨号", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4008501626"));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}