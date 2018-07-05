package com.windyroad.nghia.alarmtasks.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.windyroad.nghia.alarmtasks.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private int PERMISSION_ALL_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //now in the onCreate of your Activity you can do something like this:
        List<String> permissionsToRequest = getPermissionsRequest(this, neededPermissions);
        //if permissionsToRequest size is > 0 you need some more permission(s)
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    permissionsToRequest.toArray(new String[0]), PERMISSION_ALL_REQUEST_CODE);
        }

        //now in the onRequestPermissionsResult you can handle everything
        //as always and proceeed if everything is ok
    }

    String[] neededPermissions = new String[]{
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE //if you can write, you can read
    };

    public static List<String> getPermissionsRequest(@NonNull Context context, @NonNull String[] permissions){
        List<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++){
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED){
                permissionsToRequest.add(permissions[i]);
            }
        }
        return permissionsToRequest;
    }
}
