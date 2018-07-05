package com.windyroad.nghia.common.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Nghia-PC on 7/23/2015.
 */
public class BaseActivity extends AppCompatActivity {
    String TAG = BaseActivity.class.getSimpleName();

    //region ACTIVITY START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "On Create");
    }

    //region BACK FROM OTHER ACTIVITY
    /**
     * hay kèm onResume
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On Resume");
    }
    //endregion
    //endregion


    //region ACTIVITY FINISH
    //region START OTHER ACTIVITY
    /**
     * hay kèm Stop
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "On Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "On Stop");
    }
    //endregion

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On Destroy");
    }
    //endregion


    //region SAVE BUNDLE
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    //endregion
}
