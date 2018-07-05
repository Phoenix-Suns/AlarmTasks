package com.windyroad.nghia.common.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class PermissionUtil {
    public static int REQUEST_ALL_PERMISSION = 1;

    static String[] listPermissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, //if you can write, you can read

    };

    /**
     * Hiện yêu cầu Nhiều Permission cùng 1 lúc
     * @param activity
     * @param permissions danh sách Permission như trên
     * @return Permissions not granted
     */
    public static String[] requestAllPermission(Activity activity, String[] permissions, int requestCode){
        // --- Request Permission ---
        String[] permissionsToRequest = getPermissionNotGranted(activity, permissions);
        if (permissionsToRequest.length > 0) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest, requestCode);
        }

        return permissionsToRequest;
    }

    public static String[] requestAllPermission(Fragment fragment, String[] permissions, int requestCode){
        // --- Request Permission ---
        String[] permissionsToRequest = getPermissionNotGranted(fragment.getActivity(), permissions);
        if (permissionsToRequest.length > 0) {
            fragment.requestPermissions(permissionsToRequest, requestCode);
        }
        return permissionsToRequest;
    }

    public static String[] requestAllPermission(AppCompatActivity activity, String[] permissions, int requestCode) {
        // --- Request Permission ---
        String[] permissionsToRequest = getPermissionNotGranted(activity, permissions);
        if (permissionsToRequest.length > 0) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest, requestCode);
        }

        return permissionsToRequest;
    }


    /**
     * Check list Permission granted
     * @param context
     * @param permissions
     * @return
     */
    public static String[] getPermissionNotGranted(Context context, String[] permissions ) {
        ArrayList<String> permissionsToRequest = new ArrayList<String>();
        for (int i=0; i<permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permissions[i]);
            }
        }
        String[] permissionsArr = permissionsToRequest.toArray(new String[permissionsToRequest.size()]);
        return permissionsArr;
    }
}
