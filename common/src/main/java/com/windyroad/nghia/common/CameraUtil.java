package com.windyroad.nghia.common;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Nghia-PC on 8/27/2015.
 * Hàm hỗ trợ Camera
 */
public class CameraUtil {
    /**
     * Kiểm tra tồn tại Camera
     * @return
     */
    public boolean isSupportCamera(Context context) {
        // this device has a camera
// no camera on this device
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
