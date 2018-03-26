package Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/3/12 0012
 * .
 */

public interface RetrofitServices {
    //http://xmyttz.com/rx/rx/send_CL 取后缀send_CL
    //请求方式为POST/GET，参数为send_CL，因为没有变量所以下面方法也不需要参数
    //ResponseBody:获取网页内容
    @GET("send_CL")
    Call<ResponseBody> getOil();

    @GET("send_GC")
    Call<ResponseBody> getGold();

    @GET("send_HSI")
    Call<ResponseBody> getHSI();

    //带参数的网址传值
    @GET
    Call<ResponseBody> getLessUrl(@Url String something);

}
