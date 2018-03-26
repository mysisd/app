package Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Bean.FuturesBean;
import Interface.RetrofitServices;
import Utils.DataHelper;
import Utils.KLineEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Utils.Constant;
import Utils.PublicSetting;
import ying_tou.yingtou.R;

import static Utils.DataRequest.getStringFromAssert;

public class MarketFragment extends Fragment implements View.OnClickListener {


    private RelativeLayout mRlOil, mRlGold, mRlHsi;
    private TextView mTvOilTitle, mTvOilPrice, mTvOilApplies, mTvOilAmplitude,
            mTvGoldTitle, mTvGoldPrice, mTvGoldApplies, mTvGoldAmplitude,
            mTvHsiTitle, mTvHsiPrice, mTvHsiApplies, mTvHsiAmplitude,
            mTvName, mTvSubName, mTvPrice, mTvApplies,
            mTvKline, mTvShare, mTvDish, mTvFive;
    private static TimerHandler timerHandler;
    private Timer timer;
    private static List<String> mRvData;
    private String mOilCommodityNo, mOilNumber, mOilTitle, mOilSubTitle, mOilPrice, mOilApplies, mOilAppliesPercent, mOilAmplitudePercent,
            mGoldCommodityNo, mGoldNumber, mGoldTitle, mGoldSubTitle, mGoldPrice, mGoldApplies, mGoldAppliesPercent, mGoldAmplitudePercent,
            mHsiCommodityNo, mHsiNumber, mHsiTitle, mHsiSubTitle, mHsiPrice, mHsiApplies, mHsiAppliesPercent, mHsiAmplitudePercent,
            mCommodityNo, mNumber;
    //下划线
    private View mUnderlineKline, mUnderlineShare, mUnderlineDish, mUnderlineFive, mDivider;
    private boolean isClicked;//
    private LinearLayout mLlStatus;
    private FrameLayout mFlStatus;
    private FragmentManager fm;
    private KLineFragment mFragKline;
    private TimeShareFragment mFragShare;
    private DishFragment mFragDish;
    private FiveFragment mFragFive;
    private boolean isFirst;//第一次进入页面.设置默认数据
    private Bundle bundle;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_market, container, false);
        bundle = new Bundle();
        fm = getFragmentManager();
        initUI(layout);
        initRvDada();
        initRv(layout);
        return layout;
    }

    private void initRv(View layout) {
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.rv_market);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RvAdapter rvAdapter = new RvAdapter();
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isFirst = true;
                Toast.makeText(getActivity(), "click:" + mRvData.get(position), Toast.LENGTH_SHORT).show();
                if (isClicked) {
                    mLlStatus.setVisibility(View.GONE);
                    mFlStatus.setVisibility(View.GONE);
                    mDivider.setVisibility(View.GONE);
                    isClicked = false;
                }

                switch (position) {
                    case 0:
                        initDefaultData();
                        break;
                    case 1:
                        mTvName.setText(mOilTitle);
                        mTvSubName.setText(mOilSubTitle);
                        mTvPrice.setText(mOilPrice);
                        mTvApplies.setText(mOilAppliesPercent);
                        if (mOilApplies != null) {
                            if (Double.parseDouble(mOilApplies) >= 0) {
                                mTvPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                                mTvApplies.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                            } else {
                                mTvPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                                mTvApplies.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                            }
                        }
                        break;

                    case 2:
                        mTvName.setText(mHsiTitle);
                        mTvSubName.setText(mHsiSubTitle);
                        mTvPrice.setText(mHsiPrice);
                        mTvApplies.setText(mHsiAppliesPercent);
                        if (mHsiApplies != null) {
                            if (Double.parseDouble(mHsiApplies) >= 0) {
                                mTvPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                                mTvApplies.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                            } else {
                                mTvPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                                mTvApplies.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                            }
                        }
                        break;

                }
            }
        });
    }

    private void initDefaultData() {
        mTvName.setText(mGoldTitle);
        mTvSubName.setText(mGoldSubTitle);
        mTvPrice.setText(mGoldPrice);
        mTvApplies.setText(mGoldAppliesPercent);
        if (mGoldApplies != null) {
            if (Double.parseDouble(mGoldApplies) >= 0) {
                mTvPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                mTvApplies.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
            } else {
                mTvPrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                mTvApplies.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
            }
        }
    }

    private void getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                .build();
        RetrofitServices services = retrofit.create(RetrofitServices.class);
        Call<ResponseBody> callOil = services.getOil();
        callOil.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<FuturesBean>>() {
                    }.getType();
                    ArrayList<FuturesBean> been = gson.fromJson(json, type);
                    for (FuturesBean fb : been) {
                        mOilCommodityNo = fb.getCommodityNo();  //GC
                        mOilNumber = fb.getContractNo1();//1801
                        mOilPrice = fb.getQLastPrice();
                        mOilApplies = fb.getQChangeRate();
                        mOilAppliesPercent = PublicSetting.getChanged(mOilApplies) + "%";
                        mOilAmplitudePercent = PublicSetting.getChanged(fb.getQSwing()) + "%";
                        mOilSubTitle = mOilCommodityNo + mOilNumber;
                        mOilTitle = Constant.US_GOLD + mOilNumber;

                        mTvOilTitle.setText(mOilTitle);
                        mTvOilPrice.setText(mOilPrice);
                        mTvOilApplies.setText(mOilAppliesPercent);
                        mTvOilAmplitude.setText(mOilAmplitudePercent);
                        if (mOilApplies != null) {
                            if (Double.parseDouble(mOilApplies) >= 0) {
                                mRlOil.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                            } else {
                                mRlOil.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
        Call<ResponseBody> callGold = services.getGold();
        callGold.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<FuturesBean>>() {
                    }.getType();
                    ArrayList<FuturesBean> been = gson.fromJson(json, type);
                    for (FuturesBean fb : been) {
                        mGoldCommodityNo = fb.getCommodityNo();  //GC
                        mGoldNumber = fb.getContractNo1();//1804
                        mGoldPrice = fb.getQLastPrice();
                        mGoldApplies = fb.getQChangeRate();
                        mGoldAppliesPercent = PublicSetting.getChanged(mGoldApplies) + "%";
                        mGoldAmplitudePercent = PublicSetting.getChanged(fb.getQSwing()) + "%";
                        mGoldSubTitle = mGoldCommodityNo + mGoldNumber;
                        mGoldTitle = Constant.US_GOLD + mGoldNumber;

                        mTvGoldTitle.setText(mGoldTitle);
                        mTvGoldPrice.setText(mGoldPrice);
                        mTvGoldApplies.setText(mGoldAppliesPercent);
                        mTvGoldAmplitude.setText(mGoldAmplitudePercent);
                        if (mGoldApplies != null) {
                            if (Double.parseDouble(mGoldApplies) >= 0) {
                                mRlGold.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                            } else {
                                mRlGold.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
        Call<ResponseBody> callHsi = services.getHSI();
        callHsi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<FuturesBean>>() {
                    }.getType();
                    ArrayList<FuturesBean> been = gson.fromJson(json, type);
                    for (FuturesBean fb : been) {
                        mHsiCommodityNo = fb.getCommodityNo();//HSI
                        mHsiNumber = fb.getContractNo1();//1802
                        mHsiPrice = fb.getQLastPrice();
                        mHsiApplies = fb.getQChangeRate();
                        mHsiAppliesPercent = PublicSetting.getChanged(mHsiApplies) + "%";
                        mHsiAmplitudePercent = PublicSetting.getChanged(fb.getQSwing()) + "%";
                        mHsiSubTitle = mHsiCommodityNo + mHsiNumber;
                        mHsiTitle = Constant.HSI + mHsiNumber;

                        mTvHsiTitle.setText(mHsiTitle);
                        mTvHsiPrice.setText(mHsiPrice);
                        mTvHsiApplies.setText(mHsiAppliesPercent);
                        mTvHsiAmplitude.setText(mHsiAmplitudePercent);
                        if (mHsiApplies != null) {
                            if (Double.parseDouble(mHsiApplies) >= 0) {
                                mRlHsi.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                            } else {
                                mRlHsi.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    private void initRvDada() {
        mRvData = new ArrayList<>();
        mRvData.add("美黄金  ");
        mRvData.add(" 美原油  ");
        mRvData.add(" 恒生指数  ");
    }

    private void initUI(View layout) {
        mRlOil = (RelativeLayout) layout.findViewById(R.id.rl_market_oil);
        mTvOilTitle = (TextView) layout.findViewById(R.id.tv_market_oil_title);
        mTvOilPrice = (TextView) layout.findViewById(R.id.tv_market_oil_price);
        mTvOilApplies = (TextView) layout.findViewById(R.id.tv_market_oil_applies);
        mTvOilAmplitude = (TextView) layout.findViewById(R.id.tv_market_oil_swing);

        mRlGold = (RelativeLayout) layout.findViewById(R.id.rl_market_gold);
        mTvGoldTitle = (TextView) layout.findViewById(R.id.tv_market_gold_title);
        mTvGoldPrice = (TextView) layout.findViewById(R.id.tv_market_gold_price);
        mTvGoldApplies = (TextView) layout.findViewById(R.id.tv_market_gold_applies);
        mTvGoldAmplitude = (TextView) layout.findViewById(R.id.tv_market_gold_swing);

        mRlHsi = (RelativeLayout) layout.findViewById(R.id.rl_market_hsi);
        mTvHsiTitle = (TextView) layout.findViewById(R.id.tv_market_hsi_title);
        mTvHsiPrice = (TextView) layout.findViewById(R.id.tv_market_hsi_price);
        mTvHsiApplies = (TextView) layout.findViewById(R.id.tv_market_hsi_applies);
        mTvHsiAmplitude = (TextView) layout.findViewById(R.id.tv_market_hsi_swing);

        mTvName = (TextView) layout.findViewById(R.id.tv_market_name);
        mTvSubName = (TextView) layout.findViewById(R.id.tv_market_sub_name);
        mTvApplies = (TextView) layout.findViewById(R.id.tv_market_applies);
        mTvPrice = (TextView) layout.findViewById(R.id.tv_market_price);

        layout.findViewById(R.id.rl_market_data).setOnClickListener(this);

        mLlStatus = (LinearLayout) layout.findViewById(R.id.ll_market_module);
        mFlStatus = (FrameLayout) layout.findViewById(R.id.frag_market);

        //K线/五档等模块下划线
        mUnderlineKline = layout.findViewById(R.id.view_market_underline_kline);
        mUnderlineShare = layout.findViewById(R.id.view_market_underline_share);
        mUnderlineDish = layout.findViewById(R.id.view_market_underline_dish);
        mUnderlineFive = layout.findViewById(R.id.view_market_underline_five);
        mDivider = layout.findViewById(R.id.view_market_divider);

        mTvKline = (TextView) layout.findViewById(R.id.tv_market_kline);
        mTvShare = (TextView) layout.findViewById(R.id.tv_market_share);
        mTvDish = (TextView) layout.findViewById(R.id.tv_market_dish);
        mTvFive = (TextView) layout.findViewById(R.id.tv_market_five);

        mTvKline.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
        mTvDish.setOnClickListener(this);
        mTvFive.setOnClickListener(this);
    }

    static class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        private OnItemClickListener mOnItemClickListener;


        public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_market, parent, false);
            return new ViewHolder(inflate);
        }

        public void onBindViewHolder(final RvAdapter.ViewHolder holder, int position) {

            holder.mTvName.setText(mRvData.get(position));


            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }

        public int getItemCount() {
            return mRvData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView mTvName;

            ViewHolder(View itemView) {
                super(itemView);
                mTvName = (TextView) itemView.findViewById(R.id.tv_item_markets_name);
            }
        }

        interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_market_data:
                if (isClicked) {
                    mLlStatus.setVisibility(View.GONE);
                    mFlStatus.setVisibility(View.GONE);
                    mDivider.setVisibility(View.GONE);
                    isClicked = false;
                } else {
                    mLlStatus.setVisibility(View.VISIBLE);
                    mFlStatus.setVisibility(View.VISIBLE);
                    mDivider.setVisibility(View.VISIBLE);
                    setChoiceItem(0);
                    isClicked = true;
                }
                break;
            case R.id.tv_market_kline:
                setChoiceItem(0);
                break;
            case R.id.tv_market_share:
                setChoiceItem(1);
                break;
            case R.id.tv_market_dish:
                setChoiceItem(2);
                break;
            case R.id.tv_market_five:
                setChoiceItem(3);
                break;
        }
    }

    /**
     * 判断当前显示是哪个模块
     */
    public void setChoiceItem(int index) {
        FragmentTransaction ft = fm.beginTransaction();
        setAttribute(ft);

        switch (index) {
            case 0:
                choiceKline(ft);
                break;
            case 1:
                choiceShare(ft);
                break;
            case 2:
                choiceDish(ft);
                break;
            case 3:
                choiceFive(ft);
                break;
        }
        ft.commit();
    }

    /**
     * 五档
     */
    private void choiceFive(FragmentTransaction ft) {

        //设置背景:黑色
        mTvFive.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
        //显示下划线
        mUnderlineFive.setVisibility(View.VISIBLE);
        //如果Fragment为空，则创建一个并添加到界面上
        if (mFragFive == null) {
            mFragFive = new FiveFragment();
            ft.add(R.id.frag_market, mFragFive);
        } else {
            //否则就显示出来
            ft.show(mFragFive);
        }
    }

    /**
     * 盘口
     */
    private void choiceDish(FragmentTransaction ft) {
        mTvDish.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));//设置背景:黑色
        mUnderlineDish.setVisibility(View.VISIBLE);//显示下划线
        if (mFragDish == null) {//如果Fragment为空，则创建一个并添加到界面上
            mFragDish = new DishFragment();
            String nameContent = mTvSubName.getText().toString().trim();
            //判断不为空
            if (!nameContent.isEmpty()) {
                if (mOilSubTitle != null) {
                    if (mOilSubTitle.equals(nameContent)) {
                        mCommodityNo = mOilCommodityNo;
                        mNumber = mOilNumber;
                    }
                }
                if (mGoldSubTitle != null) {
                    if (mGoldSubTitle.equals(nameContent)) {
                        mCommodityNo = mGoldCommodityNo;
                        mNumber = mGoldNumber;
                    }
                }
                if (mHsiSubTitle != null) {
                    if (mHsiSubTitle.equals(nameContent)) {
                        mCommodityNo = mHsiCommodityNo;
                        mNumber = mHsiNumber;
                    }
                }
            }
            bundle.putString(Constant.COMMODITY_NO, mCommodityNo);
            bundle.putString(Constant.NUMBER, mNumber);
            Log.e("COMMODITY_NO", mCommodityNo + "1");
            Log.e("NUMBER", mNumber + "1");
            mFragDish.setArguments(bundle);

            ft.add(R.id.frag_market, mFragDish);
        } else {
            //否则就显示出来
            ft.show(mFragDish);
        }

    }

    /**
     * 分时
     */
    private void choiceShare(FragmentTransaction ft) {
        //设置背景:黑色
        mTvShare.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
        //显示下划线
        mUnderlineShare.setVisibility(View.VISIBLE);
        //如果Fragment为空，则创建一个并添加到界面上
        if (mFragShare == null) {
            mFragShare = new TimeShareFragment();
            ft.add(R.id.frag_market, mFragShare);
        } else {
            //否则就显示出来
            ft.show(mFragShare);
        }
    }

    /**
     * K线
     */
    private void choiceKline(FragmentTransaction ft) {
        //设置背景:黑色
        mTvKline.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDark));
        //显示下划线
        mUnderlineKline.setVisibility(View.VISIBLE);
        //如果Fragment为空，则创建一个并添加到界面上
        if (mFragKline == null) {
            mFragKline = new KLineFragment();
            ft.add(R.id.frag_market, mFragKline);
        } else {
            //否则就显示出来
            ft.show(mFragKline);
        }
    }

    /**
     * 当选中其中一个选项卡时，其他选项卡还原为默认(字体灰色,背景白色), 隐藏frag和下划线
     */
    private void setAttribute(FragmentTransaction ft) {
        //字体:灰色
        mTvKline.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvShare.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvDish.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        mTvFive.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorCommon));
        //背景:白色
        mTvKline.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvShare.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvDish.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mTvFive.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        //隐藏下划线
        mUnderlineKline.setVisibility(View.GONE);
        mUnderlineShare.setVisibility(View.GONE);
        mUnderlineDish.setVisibility(View.GONE);
        mUnderlineFive.setVisibility(View.GONE);
        //隐藏Fragment
        if (mFragKline != null) {
            ft.hide(mFragKline);
        }
        if (mFragShare != null) {
            ft.hide(mFragShare);
        }
        if (mFragDish != null) {
            ft.hide(mFragDish);
        }
        if (mFragFive != null) {
            ft.hide(mFragFive);
        }
    }


    private class TimerHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //定时器代码实现地方
            getRetrofit();
            if (!isFirst) {
                initDefaultData();

            }

        }
    }

    private class MyTimerTask extends TimerTask {
        public void run() {
            timerHandler.sendEmptyMessage(0);
        }
    }

    //返回的时候会再调一次
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        timerHandler = new TimerHandler();//实例化TimerHandler
        timer = new Timer();//设置定时器Timer
        timer.schedule(new MyTimerTask(), 0, 1000);//1秒刷新一次(0表示无延迟,1000表示隔1000ms)
    }

    //跳转到其他页面时,隐藏当前页面时调用
    public void onPause() {
        super.onPause();
        Log.e("onPause", "onPause");
        if (timer != null) {//关闭定时器
            timer.cancel();
            timer = null;
        }
    }

    //关闭程序时调用
    public void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
