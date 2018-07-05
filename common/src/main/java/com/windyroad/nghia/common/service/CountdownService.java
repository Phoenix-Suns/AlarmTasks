package com.windyroad.nghia.common.service;

import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;


public class CountdownService extends Service {

    public static final String EXTRA_MILLISECOND_UNTIL_FINISH = "com.windyroad.nghia.common.service.extra.MILLISECOND_UNTIL_FINISH";
    public static final String ACTION_COUNTDOWN = "com.windyroad.nghia.common.service.action.COUNTDOWN";

    private static final String TAG = "COUNTDOWN_SERVICE";
    private static final String EXTRA_COUNTDOWN_TIME = "com.windyroad.nghia.common.service.extra.COUNTDOWNTIME";
    private static final long COUNTDOWN_INTERVAL = 1000 - 1;  // GỜI SAU 1000 MILLISECOND

    private CountDownTimer mCountDownTimer;  // biến đếm


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_COUNTDOWN.equals(action)){
                final long countDownTime = intent.getLongExtra(EXTRA_COUNTDOWN_TIME, 0);
                handleActionCountdown(countDownTime);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCountDownTimer.cancel();
    }

    /**
     * Bắt đầu service Countdown
     * @param context
     * @param countDownTime thời gian đếm ngược (milisecond)
     */
    public static void startActionCountdown(Context context, long countDownTime){
        Intent intent = new Intent(context, CountdownService.class);
        intent.setAction(ACTION_COUNTDOWN);
        intent.putExtra(EXTRA_COUNTDOWN_TIME, countDownTime);
        context.startService(intent);
    }

    /**
     * Bắt được thời gian gởi lên
     * @param countDownTime
     */
    private void handleActionCountdown(long countDownTime) {
        Log.i(TAG, "Timer start...");

        mCountDownTimer = new CountDownTimer(countDownTime, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                publishResult(millisUntilFinished);  // gởi xuống client
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finish...");
                publishResult(0);
            }
        };
        mCountDownTimer.start();
    }

    /**
     * Trả kết quả về Client
     * @param millisUntilFinished thời gian còn lại
     */
    private void publishResult(long millisUntilFinished) {
        Intent intent = new Intent(ACTION_COUNTDOWN);
        intent.putExtra(EXTRA_MILLISECOND_UNTIL_FINISH, millisUntilFinished);
        sendBroadcast(intent);
    }


}
