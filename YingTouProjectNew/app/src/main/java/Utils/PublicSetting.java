package Utils;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/3/12 0012
 * .
 */

public class PublicSetting {
    /**
     * 手机号码输入格式||..
     */
    public static boolean isMobile(String number) {
        String num = "[1][34578]\\d{9}";//第一位是 1，第二位是34578中的一个，后面还有9位数
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            return number.matches(num);
        }
    }

    /**
     * 设置2秒后自动跳转
     *
     * @param context  ：Toast弹出内容
     * @param activity ：传入当前Activity
     */
    public static void autoDelay(String context, final Activity activity) {
        Toast toast = Toast.makeText(activity, context + ",将在2秒后自动跳转。。。", Toast.LENGTH_SHORT);
        //设置Toast弹出位置为居中
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                activity.finish();
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    /**
     * //将数字转成只获取小数点后两位.并且不进行四舍五入
     */
    public static String getChanged(String number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        BigDecimal bd = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        return String.valueOf(bd);
    }
}

