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

public class InformationFragment extends Fragment implements View.OnClickListener{


    private TextView mTvActivities, mTvRelated, mTvNews, mTvControl;
    private ActivitiesFragment mFragActivities;
    private RelatedFragment mFragRelated;
    private NewsFragment mFragNews;
    private ControlFragment mFragControl;
    private FragmentManager fm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_information, container, false);
        fm = getFragmentManager();
        mTvActivities = (TextView) layout.findViewById(R.id.tv_information_activity);
        mTvRelated = (TextView) layout.findViewById(R.id.tv_information_related);
        mTvNews = (TextView) layout.findViewById(R.id.tv_information_news);
        mTvControl = (TextView) layout.findViewById(R.id.tv_information_control);
        mTvActivities.setOnClickListener(this);
        mTvRelated.setOnClickListener(this);
        mTvNews.setOnClickListener(this);
        mTvControl.setOnClickListener(this);
        setChoiceItem(0);
        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_information_activity:
                setChoiceItem(0);
                break;
            case R.id.tv_information_related:
                setChoiceItem(1);
                break;
            case R.id.tv_information_news:
                setChoiceItem(2);
                break;
            case R.id.tv_information_control:
                setChoiceItem(3);
                break;
        }
    }
    public void setChoiceItem(int choiceItem) {
        FragmentTransaction ft = fm.beginTransaction();
        setAttribute(ft);
        switch (choiceItem) {
            case 0:
                mTvActivities.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
                mTvActivities.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                //如果Fragment为空，则创建一个并添加到界面上
                if (mFragActivities == null) {
                    mFragActivities = new ActivitiesFragment();
                    ft.add(R.id.frag_information, mFragActivities);
                } else {
                    //否则就显示出来
                    ft.show(mFragActivities);
                }
                break;
            case 1:
                mTvRelated.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
                mTvRelated.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                //如果Fragment为空，则创建一个并添加到界面上
                if (mFragRelated == null) {
                    mFragRelated = new RelatedFragment();
                    ft.add(R.id.frag_information, mFragRelated);
                } else {
                    //否则就显示出来
                    ft.show(mFragRelated);
                }
                break;
            case 2:
                mTvNews.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
                mTvNews.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                //如果Fragment为空，则创建一个并添加到界面上
                if (mFragNews == null) {
                    mFragNews = new NewsFragment();
                    ft.add(R.id.frag_information, mFragNews);
                } else {
                    //否则就显示出来
                    ft.show(mFragNews);
                }
                break;
            case 3:
                mTvControl.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
                mTvControl.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                //如果Fragment为空，则创建一个并添加到界面上
                if (mFragControl == null) {
                    mFragControl = new ControlFragment();
                    ft.add(R.id.frag_information, mFragControl);
                } else {
                    //否则就显示出来
                    ft.show(mFragControl);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 点击时候设置各个tv的字体颜色、背景颜色及frag的隐藏
     */
    private void setAttribute(FragmentTransaction ft) {
        mTvActivities.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvActivities.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvRelated.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvRelated.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvNews.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvNews.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvControl.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvControl.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        if (mFragActivities != null) {
            ft.hide(mFragActivities);
        }
        if (mFragRelated != null) {
            ft.hide(mFragRelated);
        }
        if (mFragNews != null) {
            ft.hide(mFragNews);
        }
        if (mFragControl != null) {
            ft.hide(mFragControl);
        }
    }
}
