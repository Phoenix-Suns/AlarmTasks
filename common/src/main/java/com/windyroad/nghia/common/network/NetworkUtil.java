package com.windyroad.nghia.common.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Nghia-PC on 9/16/2015.
 */
public class NetworkUtil {

    /**
     * Loại kết nối, không kết nối, qua wifi, qua mạng
     */
    public enum ConnectionType {
        NOT_CONNECTED,
        TYPE_WIFI,
        TYPE_ETHERNET, TYPE_MOBILE_DATA
    }

    /**
     * Lấy phương thức kết nối Mạng
     * @param context
     * @return
     */
    public static ConnectionType getConnectivityStatus(Context context){
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null){
            switch (networkInfo.getType()){
                case ConnectivityManager.TYPE_WIFI:
                    return ConnectionType.TYPE_WIFI;
                case ConnectivityManager.TYPE_MOBILE:
                    return ConnectionType.TYPE_MOBILE_DATA;
                case ConnectivityManager.TYPE_ETHERNET:
                    return ConnectionType.TYPE_ETHERNET;
            }
        }
        return ConnectionType.NOT_CONNECTED;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
