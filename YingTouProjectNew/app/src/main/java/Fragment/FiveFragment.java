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

/**
 * A simple {@link Fragment} subclass.
 */
public class FiveFragment extends Fragment {

    private TextView mTvSellPrice1, mTvSellPrice2, mTvSellPrice3, mTvSellPrice4, mTvSellPrice5,//卖价1.2.3.4.5
            mTvSellQuantity1, mTvSellQuantity2, mTvSellQuantity3, mTvSellQuantity4, mTvSellQuantity5,//卖量1.2.3.4.5
            mTvBuyPrice1, mTvBuyPrice2, mTvBuyPrice3, mTvBuyPrice4, mTvBuyPrice5,//买价1.2.3.4.5
            mTvBuyQuantity1, mTvBuyQuantity2, mTvBuyQuantity3, mTvBuyQuantity4, mTvBuyQuantity5;//,买量1.2.3.4.5
    private String commodityNo, number, sellPrice, buyPrice, mUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_five, container, false);
        initUI(layout);

        return layout;
    }

    private void initUI(View layout) {
        mTvSellPrice1 = (TextView) layout.findViewById(R.id.tv_five_sell1);
        mTvSellPrice2 = (TextView) layout.findViewById(R.id.tv_five_sell2);
        mTvSellPrice3 = (TextView) layout.findViewById(R.id.tv_five_sell3);
        mTvSellPrice4 = (TextView) layout.findViewById(R.id.tv_five_sell4);
        mTvSellPrice5 = (TextView) layout.findViewById(R.id.tv_five_sell5);
        mTvSellQuantity1 = (TextView) layout.findViewById(R.id.tv_five_sell_quantity1);
        mTvSellQuantity2 = (TextView) layout.findViewById(R.id.tv_five_sell_quantity2);
        mTvSellQuantity3 = (TextView) layout.findViewById(R.id.tv_five_sell_quantity3);
        mTvSellQuantity4 = (TextView) layout.findViewById(R.id.tv_five_sell_quantity4);
        mTvSellQuantity5 = (TextView) layout.findViewById(R.id.tv_five_sell_quantity5);
        mTvBuyPrice1 = (TextView) layout.findViewById(R.id.tv_five_buy1);
        mTvBuyPrice2 = (TextView) layout.findViewById(R.id.tv_five_buy2);
        mTvBuyPrice3 = (TextView) layout.findViewById(R.id.tv_five_buy3);
        mTvBuyPrice4 = (TextView) layout.findViewById(R.id.tv_five_buy4);
        mTvBuyPrice5 = (TextView) layout.findViewById(R.id.tv_five_buy5);
        mTvBuyQuantity1 = (TextView) layout.findViewById(R.id.tv_five_buy_quantity1);
        mTvBuyQuantity2 = (TextView) layout.findViewById(R.id.tv_five_buy_quantity2);
        mTvBuyQuantity3 = (TextView) layout.findViewById(R.id.tv_five_buy_quantity3);
        mTvBuyQuantity4 = (TextView) layout.findViewById(R.id.tv_five_buy_quantity4);
        mTvBuyQuantity5 = (TextView) layout.findViewById(R.id.tv_five_buy_quantity5);
    }
}
