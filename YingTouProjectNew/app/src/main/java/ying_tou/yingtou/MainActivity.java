package ying_tou.yingtou;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import Fragment.BBSFragment;
import Fragment.InformationFragment;
import Fragment.MineFragment;
import Fragment.RulesFragment;
import Fragment.TradingFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLlRules, mLlBBS, mLlTrading, mLlInformation, mLlMine;
    private TextView mTvRules, mTvBBS, mTvTrading, mTvInformation, mTvMine;
    //定义FragmentManager对象管理器
    private FragmentManager fm;

    private RulesFragment mFragRules;
    private BBSFragment mFragBBS;
    private TradingFragment mFragTrading;
    private InformationFragment mFragInformation;
    private MineFragment mFragMine;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        initUI();//初始化界面控件
        setChoiceItem(2);//初始化页面加载时显示第一个选项卡
    }

    private void initUI() {
        mLlRules = (LinearLayout) findViewById(R.id.ll_main_rules);
        mLlBBS = (LinearLayout) findViewById(R.id.ll_main_bbs);
        mLlTrading = (LinearLayout) findViewById(R.id.ll_main_trading);
        mLlInformation = (LinearLayout) findViewById(R.id.ll_main_information);
        mLlMine = (LinearLayout) findViewById(R.id.ll_main_mine);

        mTvRules = (TextView) findViewById(R.id.tv_main_rules);
        mTvBBS = (TextView) findViewById(R.id.tv_main_bbs);
        mTvTrading = (TextView) findViewById(R.id.tv_main_trading);
        mTvInformation = (TextView) findViewById(R.id.tv_main_information);
        mTvMine = (TextView) findViewById(R.id.tv_main_mine);

        mLlRules.setOnClickListener(this);
        mLlBBS.setOnClickListener(this);
        mLlTrading.setOnClickListener(this);
        mLlInformation.setOnClickListener(this);
        mLlMine.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_rules:
                setChoiceItem(0);
                break;
            case R.id.ll_main_bbs:
                setChoiceItem(1);
                break;
            case R.id.ll_main_trading:
                setChoiceItem(2);
                break;
            case R.id.ll_main_information:
                setChoiceItem(3);
                break;
            case R.id.ll_main_mine:
                setChoiceItem(4);
                break;
        }
    }

    public void setChoiceItem(int index) {
        FragmentTransaction ft = fm.beginTransaction();
        // 清空, 重置选项, 隐藏Fragment
        setAttribute(ft);
        switch (index) {
            case 0:
                // 旧版getResources().getColor过时
                // mTvMine.setTextColor(getResources().getColor(R.color.colorDark));
                mTvRules.setTextColor(ContextCompat.getColor(this, R.color.colorDark));//新版
                mLlRules.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue));
                //如果mFragment为空，则创建一个并添加到界面上
                if (mFragRules == null) {
                    mFragRules = new RulesFragment();
                    ft.add(R.id.frag_main, mFragRules);
                } else {
                    //否则就显示出来
                    ft.show(mFragRules);
                }
                break;
            case 1:
                // 旧版getResources().getColor过时
                // mTvMine.setTextColor(getResources().getColor(R.color.colorDark));
                mTvBBS.setTextColor(ContextCompat.getColor(this, R.color.colorDark));//新版
                mLlBBS.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue));
                //如果mFragment为空，则创建一个并添加到界面上
                if (mFragBBS == null) {
                    mFragBBS = new BBSFragment();
                    ft.add(R.id.frag_main, mFragBBS);
                } else {
                    //否则就显示出来
                    ft.show(mFragBBS);
                }
                break;
            case 2:
                // 旧版getResources().getColor过时
                // mTvMine.setTextColor(getResources().getColor(R.color.colorDark));
                mTvTrading.setTextColor(ContextCompat.getColor(this, R.color.colorDark));//新版
                mLlTrading.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue));
                //如果mFragment为空，则创建一个并添加到界面上
                if (mFragTrading == null) {
                    mFragTrading = new TradingFragment();
                    ft.add(R.id.frag_main, mFragTrading);
                } else {
                    //否则就显示出来
                    ft.show(mFragTrading);
                }
                break;
            case 3:
                // 旧版getResources().getColor过时
                // mTvMine.setTextColor(getResources().getColor(R.color.colorDark));
                mTvInformation.setTextColor(ContextCompat.getColor(this, R.color.colorDark));//新版
                mLlInformation.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue));

                //如果marketFragment为空，则创建一个并添加到界面上
                if (mFragInformation == null) {
                    mFragInformation = new InformationFragment();
                    ft.add(R.id.frag_main, mFragInformation);
                } else {
                    //否则就显示出来
                    ft.show(mFragInformation);
                }
                break;
            case 4:
                // 旧版getResources().getColor过时
                // mTvMine.setTextColor(getResources().getColor(R.color.colorDark));
                mTvMine.setTextColor(ContextCompat.getColor(this, R.color.colorDark));//新版
                mLlMine.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlue));

                //如果marketFragment为空，则创建一个并添加到界面上
                if (mFragMine == null) {
                    mFragMine = new MineFragment();
                    ft.add(R.id.frag_main, mFragMine);
                } else {
                    //否则就显示出来
                    ft.show(mFragMine);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认 及frag的隐藏
     */
    private void setAttribute(FragmentTransaction ft) {
        mLlRules.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLlBBS.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLlTrading.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLlInformation.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        mLlMine.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));

        mTvRules.setTextColor(ContextCompat.getColor(this, R.color.colorCommon));
        mTvBBS.setTextColor(ContextCompat.getColor(this, R.color.colorCommon));
        mTvTrading.setTextColor(ContextCompat.getColor(this, R.color.colorCommon));
        mTvInformation.setTextColor(ContextCompat.getColor(this, R.color.colorCommon));
        mTvMine.setTextColor(ContextCompat.getColor(this, R.color.colorCommon));

        if (mFragRules != null) {
            ft.hide(mFragRules);
        }
        if (mFragBBS != null) {
            ft.hide(mFragBBS);
        }
        if (mFragTrading != null) {
            ft.hide(mFragTrading);
        }
        if (mFragInformation != null) {
            ft.hide(mFragInformation);
        }
        if (mFragMine != null) {
            ft.hide(mFragMine);
        }
    }
}
