package Utils;

import com.github.tifezh.kchartlib.chart.EntityImpl.MinuteLineImpl;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/22 0022.
 * 分时图实体
 */

public class MinuteLineEntity implements MinuteLineImpl{
    /**
     * time : 09:30
     * price : 3.53
     * avg : 3.5206
     * vol : 9251
     */

    public Date time;
    public float price;
    public float avg;
    public int vol;

    @Override
    public float getAvgPrice() {
        return avg;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public Date getDate() {
        return time;
    }
}
