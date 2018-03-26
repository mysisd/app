package Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Bean.FuturesBean;
import Bean.KLineBean;
import Interface.RetrofitServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ying_tou.yingtou.R;

/**
 * Created by Administrator on 2018/3/21 0021.
 * 模拟网络请求
 */

public class DataRequest {
    private static List<KLineEntity> datas = null;
    private static Random random = new Random();

    public static String getStringFromAssert(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<KLineEntity> getALL(Context context) {


        if (datas == null) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(Constant.URL)
//                    .build();
//            RetrofitServices services = retrofit.create(RetrofitServices.class);
//            Call<ResponseBody> call = services.getLessUrl("echart?value=GC&num=1804");
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        String json = response.body().string();
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<ArrayList<KLineEntity>>() {
//                        }.getType();
//                        ArrayList<KLineEntity> been = gson.fromJson(json, type);
////                        for (KLineBean kb : been) {
////                        String d = kb.getDateTimeStamp();
////                        String d1 = kb.getDateTimeStamp();+
////                        String d2 = kb.getDateTimeStamp();
////                        String d3= kb.getDateTimeStamp();
////                        String d4 = kb.getDateTimeStamp();
////                        String d5 = kb.getDateTimeStamp();
////                            datas.add(new KLineEntity(d1,d2,d3,d4,d5,d));
////
////                        }
//
//                        DataHelper.calculate(datas);
//                        datas = been;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                }
//            });
            Gson gson = new Gson();
            String json = getStringFromAssert(context, "ibm.json");
            Type type = new TypeToken<List<KLineEntity>>() {
            }.getType();
            final List<KLineEntity> data = gson.fromJson(json, type);

            DataHelper.calculate(data);
            datas = data;

            for (KLineEntity ke:data){
                Log.e("1", "1" + ke.getDatetime());
            }
        }
        return datas;
    }

    /**
     * 分页查询
     *
     * @param context
     * @param offset  开始的索引
     * @param size    每次查询的条数
     */
    public static List<KLineEntity> getData(Context context, int offset, int size) {
        List<KLineEntity> all = getALL(context);
        List<KLineEntity> data = new ArrayList<>();
        int start = Math.max(0, all.size() - 1 - offset - size);
        int stop = Math.min(all.size(), all.size() - offset);
        for (int i = start; i < stop; i++) {
            data.add(all.get(i));
        }
        return data;
    }

    /**
     * 随机生成分时数据
     */
    public static List<MinuteLineEntity>
    getMinuteData(Context context,
                  @NonNull Date startTime,
                  @NonNull Date endTime,
                  @Nullable Date firstEndTime,
                  @Nullable Date secondStartTime) {
        List<MinuteLineEntity> list = new ArrayList<>();
        long startDate = startTime.getTime();
        if (firstEndTime == null && secondStartTime == null) {
            while (startDate <= endTime.getTime()) {
                MinuteLineEntity data = new MinuteLineEntity();
                data.time = new Date(startDate);
                startDate += 60000;
                list.add(data);
            }
        } else {
            while (startDate <= firstEndTime.getTime()) {
                MinuteLineEntity data = new MinuteLineEntity();
                data.time = new Date(startDate);
                startDate += 60000;
                list.add(data);
            }
            startDate = secondStartTime.getTime();
            while (startDate <= endTime.getTime()) {
                MinuteLineEntity data = new MinuteLineEntity();
                data.time = new Date(startDate);
                startDate += 60000;
                list.add(data);
            }
        }
        randomLine(list);
        float sum = 0;
        for (int i = 0; i < list.size(); i++) {
            MinuteLineEntity data = list.get(i);
            sum += data.price;
            data.avg = 1f * sum / (i + 1);
        }
        return list;
    }

    /**
     * 生成随机曲线
     */
    private static void randomLine(List<MinuteLineEntity> list) {
        float STEP_MAX = 0.9f;
        float STEP_CHANGE = 1f;
        float HEIGHT_MAX = 200;

        float height = (float) (Math.random() * HEIGHT_MAX);
        float slope = (float) ((Math.random() * STEP_MAX) * 2 - STEP_MAX);

        for (int x = 0; x < list.size(); x++) {
            height += slope;
            slope += (Math.random() * STEP_CHANGE) * 2 - STEP_CHANGE;

            if (slope > STEP_MAX) {
                slope = STEP_MAX;
            }
            if (slope < -STEP_MAX) {
                slope = -STEP_MAX;
            }

            if (height > HEIGHT_MAX) {
                height = HEIGHT_MAX;
                slope *= -1;
            }
            if (height < 0) {
                height = 0;
                slope *= -1;
            }

            list.get(x).price = height + 1000;
        }
    }
}

