package com.windyroad.nghia.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.windyroad.nghia.common.fragment.ProgressDialogFragment;
import com.windyroad.nghia.common.fragment.WaitingDialogFragment;

/**
 * Created by Nghia-PC on 8/27/2015.
 * Các Dialog hỗ trợ nhanh
 */
public class DialogUtil {
    /**
     * Hiện Dialog Mở GPS
     * */
    public static void showLocationSettingAlert(
            final Context context, String title, String message, String positiveButtonText, String negativeButtonText){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setTitle(title).setMessage(message);

        alertDialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    /**
     * Mở Dialog hỏi mở Mobile Data
     */
    public static void showNetworkSettingAlert(
            final Context context, String title, String message, String positiveButtonText, String negativeButtonText){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, android.support.v7.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setTitle(title).setMessage(message);

        alertDialog.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * Ẩn/Hiện Progress Đang hoạt động
     * Ko tắt khi ở ngoài, không cho hủy
     * Deprecate at 26 API
     * @param progressDialog
     * @param dismissDlg
     * @deprecated
     */
    @Deprecated
    public static void showProgressDialog(
            ProgressDialog progressDialog, boolean dismissDlg, boolean allowCancel, @Nullable String title,@Nullable String message) {

        if (dismissDlg) {

            if (title != null)
                progressDialog.setTitle(title);
            if (message != null)
                progressDialog.setMessage(message);

            // không cho hủy
            if (!allowCancel) {
                progressDialog.setCancelable(false);  // không cho hủy
                progressDialog.setCanceledOnTouchOutside(false);  // không ẩn khi click ngoài
            }

            progressDialog.show();

        } else {
            progressDialog.dismiss();
            progressDialog.cancel();  // giống dismiss, gọi hàm dialog.cancellistner
        }
    }

    public static void showProgressDialog(Activity activity, String dlgTag, boolean closeDlg, boolean allowCancel, @Nullable String title, @Nullable String message) {

        FragmentManager manager = activity.getFragmentManager();
        ProgressDialogFragment dialog = (ProgressDialogFragment) manager.findFragmentByTag(dlgTag);
        if (dialog == null) dialog = ProgressDialogFragment.newInstance(title, false);

        dialog.setMessage(message);

        if (closeDlg) {
            dialog.dismiss();
        } else {
            dialog.setCancelable(false);
            dialog.show(manager, dlgTag);
        }
    }

    public static void showWaitingDialog(Activity activity, String dlgTag, boolean allowCancel) {

        FragmentManager manager = activity.getFragmentManager();
        WaitingDialogFragment dialog = (WaitingDialogFragment) manager.findFragmentByTag(dlgTag);
        if (dialog == null) dialog = WaitingDialogFragment.newInstance(allowCancel);

        dialog.setCancelable(allowCancel);
        dialog.show(manager, dlgTag);
    }

    public static void hideWaitingDialog(Activity activity, String dlgTag) {
        FragmentManager manager = activity.getFragmentManager();
        WaitingDialogFragment dialog = (WaitingDialogFragment) manager.findFragmentByTag(dlgTag);

        if (dialog != null)
            dialog.dismiss();
    }
}
