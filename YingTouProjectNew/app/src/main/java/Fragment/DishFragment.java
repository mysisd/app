package Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ying_tou.yingtou.R;

public class DishFragment extends Fragment {

    private TextView mTvBuy, mTvRiseOrFall, mTvOpen, mTvHighest, mTvVolume, mTvHoldings, mTvSell, mTvApplies, mTvYesterday, mTvLowest, mTvAmplitude;
    private String commodityNo, number, sellPrice, buyPrice, mUserId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dish, container, false);

        initUI(layout);
        return layout;
    }

    private void initUI(View layout) {
        mTvBuy = (TextView) layout.findViewById(R.id.tv_dish_buy);
        mTvRiseOrFall = (TextView) layout.findViewById(R.id.tv_dish_rise_fall);
        mTvOpen = (TextView) layout.findViewById(R.id.tv_dish_open);
        mTvHighest = (TextView) layout.findViewById(R.id.tv_dish_highest);
        mTvVolume = (TextView) layout.findViewById(R.id.tv_dish_volume);
        mTvHoldings = (TextView) layout.findViewById(R.id.tv_dish_holdings);
        mTvSell = (TextView) layout.findViewById(R.id.tv_dish_sell);
        mTvApplies = (TextView) layout.findViewById(R.id.tv_dish_applies);
        mTvYesterday = (TextView) layout.findViewById(R.id.tv_dish_yesterday);
        mTvLowest = (TextView) layout.findViewById(R.id.tv_dish_lowest);
        mTvAmplitude = (TextView) layout.findViewById(R.id.tv_dish_amplitude);
    }

}
