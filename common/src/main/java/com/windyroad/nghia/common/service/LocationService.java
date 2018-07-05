package com.windyroad.nghia.common.service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Nghia-PC on 7/16/2015.
 * Lấy GPS Thông qua Service - Không có tùy
 */
public class LocationService extends Service {

    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 99;
    Location mLocation;  // Dữ liệu trả về
    boolean mIsGPSEnabled = false;  // flag for GPS status
    boolean mIsNetworkEnabled = false;  // flag for network status
    boolean mCanGetLocation = false;

    private static final long DEFAULT_MIN_METER_UPDATE_DISTANCE = 10; // 10 meters
    private static final long DEFAULT_MIN_MILLISECOND_UPDATE_TIME = 1; // 1/1000s
    /** Khoảng cách Update */
    private long minMeterUpdateDistance;
    /** Thời gian Update */
    private long minMillisecondUpdateTime;
    private final Context mContext;
    protected LocationManager mLocationManager;
    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    public LocationService(Context context) {
        this.mContext = context;
        mLocation = new Location("Some Where");
        mLocation.setLatitude(0.0);
        mLocation.setLongitude(0.0);

        minMeterUpdateDistance = DEFAULT_MIN_METER_UPDATE_DISTANCE;
        minMillisecondUpdateTime = DEFAULT_MIN_MILLISECOND_UPDATE_TIME;
        getLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


        mLocationListener = null;
    }

    public Location getLocation() {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            mIsGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            mIsNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!mIsGPSEnabled && !mIsNetworkEnabled) {
                // Không lấy được GPS
            } else {
                this.mCanGetLocation = true;
                if (mIsNetworkEnabled) {
                    getLocationByNetwork();
                }
                if (mIsGPSEnabled) {
                    getLocationByGPS();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mLocation;
    }

    public double getLatitude() {
        if (mLocation != null) {
            return mLocation.getLatitude();
        } else {
            return 0;
        }
    }

    public double getLongitude() {
        if (mLocation != null) {
            return mLocation.getLongitude();
        } else {
            return 0;
        }
    }

    public boolean canGetLocation() {
        return this.mCanGetLocation;
    }

    /** Dừng GPS */
    public void stopUsingGPS() {
        //Check + Request Permission
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)mContext, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
            return;
        }

        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }


    /** Lấy vị trí qua GPS */
    private void getLocationByGPS() {
        Log.d("Location", "Get by GPS");

        //Check + Request Permission
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)mContext, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
            return;
        }

        if (mLocation == null) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minMillisecondUpdateTime,
                    minMeterUpdateDistance,
                    mLocationListener);
            if (mLocationManager != null) {
                Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) mLocation = location;
            }
        }
    }

    /** Lấy vị trí Qua mạng */
    private void getLocationByNetwork() {
        Log.d("Location", "Get by Network");

        //Check + Request Permission
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)mContext, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
            return;
        }

        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minMillisecondUpdateTime,
                minMeterUpdateDistance,
                mLocationListener);

        if (mLocationManager != null) {
            Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) mLocation = location;
        }
    }
}
