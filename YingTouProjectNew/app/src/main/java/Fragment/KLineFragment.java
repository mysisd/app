package Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tifezh.kchartlib.chart.BaseKChartView;
import com.github.tifezh.kchartlib.chart.KChartView;
import com.github.tifezh.kchartlib.chart.formatter.DateFormatter;

import java.util.List;

import Adapter.KChartAdapter;
import Utils.DataRequest;
import Utils.KLineEntity;
import ying_tou.yingtou.R;

public class KLineFragment extends Fragment {

    private KChartView mKChartView;
    private KChartAdapter mKChartAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_kline, container, false);

        mKChartView = (KChartView) layout.findViewById(R.id.kline_kChartView);
        mKChartAdapter = new KChartAdapter();
        mKChartView.setAdapter(mKChartAdapter);
        mKChartView.setDateTimeFormatter(new DateFormatter());
        mKChartView.setGridRows(4);
        mKChartView.setGridColumns(4);

        mKChartView.setOnSelectedChangedListener(new BaseKChartView.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(BaseKChartView view, Object point, int index) {
                KLineEntity data = (KLineEntity) point;
                Log.e("onSelectedChanged", "index:" + index + " closePrice:" + data.getClosePrice());

            }
        });
        initData();
        return layout;
    }

    private void initData() {
        mKChartView.showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<KLineEntity> data = DataRequest.getALL(getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mKChartAdapter.addFooterData(data);
                        mKChartView.startAnimation();
                        mKChartView.refreshEnd();
                    }
                });
            }
        }).start();
    }

}
