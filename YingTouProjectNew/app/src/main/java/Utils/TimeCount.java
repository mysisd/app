package Utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by Administrator on 2018/3/12 0012.
 * 验证码倒计时
 */

public class TimeCount extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to {@link #start()} until the countdown is done and {@link #onFinish()}
     * is called.
     * @param countDownInterval The interval along the way to receive
     * {@link #onTick(long)} callbacks.
     */
    private Button btn;

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * @param millisInFuture:总时长
     * @param countDownInterval:计时的时间间隔
     * @param btn:响应的按钮
     */
    public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    //计时过程显示
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);
        btn.setText(" " + millisUntilFinished / 1000 + "秒后重新发送验证码 ");
        btn.setBackgroundColor(Color.parseColor("#686868"));
        btn.setTextSize(12);
    }

    //计时完毕时触发
    public void onFinish() {
        btn.setText("重新验证");
        btn.setClickable(true);
        btn.setBackgroundColor(Color.parseColor("#0379B5"));
        btn.setTextSize(13);
    }
}