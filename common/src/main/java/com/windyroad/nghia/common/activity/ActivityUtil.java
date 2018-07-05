package com.windyroad.nghia.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Nghia-PC on 7/9/2015.
 */
public class ActivityUtil {

    /**
     * Finish Activity after few second
     * @param delaySecond
     * @param currentActivity
     * @param toActivity
     */
    public static void delayFinishActivity(long delaySecond, final Activity currentActivity, final Class<?> toActivity) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(currentActivity.getBaseContext(), toActivity);
                currentActivity.startActivity(mainIntent);
                currentActivity.finish();
            }
        }, delaySecond);
    }

    public static void delayFinishActivity(long delaySecond, final Activity currentActivity, final Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentActivity.startActivity(intent);
                currentActivity.finish();
            }
        }, delaySecond);
    }

    /**
     * Đặt kết quả trả về Activity
     * @param activity
     * @param data
     */
    public static void setResultOk(Activity activity, Intent data) {
        if (activity.getParent() == null) {
            activity.setResult(Activity.RESULT_OK, data);
        } else {
            activity.getParent().setResult(Activity.RESULT_OK, data);
        }
    }

    /**
     * Cài đặt Toolbar trong Activity
     * @param appCompatActivity Activity
     * @param toolbarId id của Toolbar
     * @param displayHomeAsUp
     * @param homeAsUpIndicatorId id ảnh của nút Home
     * @param displayIcon
     * @param iconId id ảnh của Icon
     */
    public static void setupToolbar(AppCompatActivity appCompatActivity, int toolbarId,
                                    boolean showTitle,
                                    boolean displayHomeAsUp, @Nullable Integer homeAsUpIndicatorId,
                                    boolean displayIcon, @Nullable Integer iconId){
        // lấy Toolbar
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(toolbarId);
        appCompatActivity.setSupportActionBar(toolbar);

        setupToolbar(appCompatActivity, showTitle, displayHomeAsUp, homeAsUpIndicatorId, displayIcon, iconId);
    }

    /**
     * Setup Toolbar for Fragment
     * @param appCompatActivity
     * @param showTitle
     * @param displayHomeAsUp
     * @param homeAsUpIndicatorId
     * @param displayIcon
     * @param iconId
     */
    public static void setupToolbar(AppCompatActivity appCompatActivity, boolean showTitle, boolean displayHomeAsUp, @Nullable Integer homeAsUpIndicatorId, boolean displayIcon, @Nullable Integer iconId) {
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            // Show Title
            if (showTitle) actionBar.setDisplayShowTitleEnabled(true);
            else actionBar.setDisplayShowTitleEnabled(false);

            // hiện Button Home hay Up
            actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUp);  // hiển thị Home/ Up

            if (homeAsUpIndicatorId != null)
                actionBar.setHomeAsUpIndicator(homeAsUpIndicatorId);  // đổi ảnh Home, Up

            // Hiện icon
            actionBar.setHomeButtonEnabled(displayIcon);

            if (iconId != null)
                actionBar.setIcon(ContextCompat.getDrawable(appCompatActivity, iconId));
        }
    }


    /**
     * mở Google Map theo tọa độ
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static Uri startOtherMap(Context context, String latitude, String longitude){
        String strUrl = String.format("geo:%s,%s?z=%s", latitude, longitude, 16);
        Uri gmmIntentUri = Uri.parse(strUrl);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //mapIntent.setPackage("com.google.android.apps.maps");  // start Google map nếu có
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }

        return gmmIntentUri;
    }
}
