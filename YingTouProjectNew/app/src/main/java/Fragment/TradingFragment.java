package Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ying_tou.yingtou.R;

public class TradingFragment extends Fragment implements View.OnClickListener {


    private TextView mTvMarket, mTvEnrollment;
    private MarketFragment mFragMarket;
    private EnrollmentFragment mFragEnrollment;
    private FragmentManager fm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_trading, container, false);
        initUI(layout);
        fm= getFragmentManager();
        setChoiceItem(0);
        return layout;
    }


    private void initUI(View layout) {
        mTvMarket = (TextView) layout.findViewById(R.id.tv_trading_market);
        mTvEnrollment = (TextView) layout.findViewById(R.id.tv_trading_enrollment);
        mTvMarket.setOnClickListener(this);
        mTvEnrollment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_trading_market:
                setChoiceItem(0);
                break;
            case R.id.tv_trading_enrollment:
                setChoiceItem(1);
                break;
        }

    }

    public void setChoiceItem(int choiceItem) {
        FragmentTransaction ft = fm.beginTransaction();
        setAttribute(ft);
        switch (choiceItem) {
            case 0:
                mTvMarket.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
                mTvMarket.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                //如果Fragment为空，则创建一个并添加到界面上
                if (mFragMarket == null) {
                    mFragMarket = new MarketFragment();
                    ft.add(R.id.frag_trading, mFragMarket);
                } else {
                    //否则就显示出来
                    ft.show(mFragMarket);
                }
                break;
            case 1:
                mTvEnrollment.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
                mTvEnrollment.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                //如果Fragment为空，则创建一个并添加到界面上
                if (mFragEnrollment == null) {
                    mFragEnrollment = new EnrollmentFragment();
                    ft.add(R.id.frag_trading, mFragEnrollment);
                } else {
                    //否则就显示出来
                    ft.show(mFragEnrollment);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 点击时候设置各个tv的字体颜色、背景颜色及frag的隐藏
     */
    private void setAttribute(FragmentTransaction ft) {
        mTvMarket.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvMarket.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvEnrollment.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvEnrollment.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        if (mFragMarket != null) {
            ft.hide(mFragMarket);
        }
        if (mFragEnrollment != null) {
            ft.hide(mFragEnrollment);
        }
    }
}
