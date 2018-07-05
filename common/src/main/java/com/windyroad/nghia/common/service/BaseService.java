package com.windyroad.nghia.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Nghia-PC on 7/16/2015.
 */
public class BaseService extends Service {
    String TAG = BaseService.class.getSimpleName();

    /**
     * Called when the service is being created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "on Create");
    }

    //region KIỂU 1 CHẠY
    /**
     * The service is starting, due to a call to
     * startService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Log.d(TAG, "on Start Command");
        //stopSelf(); stopSelfResult();  // tự dừng
        // hàm dừng
        // stopService(new Intent(getBaseContext(), MyService.class)); => onDestroy
        return START_STICKY;
        //return START_NOT_STICKY;
        //return START_REDELIVER_INTENT;
        //return mStartMode;
    }
    //endregion


    //region KIỂU 2 CHẠY LIÊN TỤC
    /**
     * A client is binding to the service with
     * bindService()
     * Sử dụng để ứng dụng khác gọi Service
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "on Bind");
        return null;
    }

    /**
     * Called when all clients have unbound with
     * unbindService()
     * Sử dụng để ứng dụng khác tắt
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "on Unbind");
        return false;
    }

    /**
     * Called when a client is binding to the service with
     * bindService()
     */
    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "on Rebind");
    }
    //endregion

    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "on Destroy");
    }
}
