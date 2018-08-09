package com.windyroad.nghia.common.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;
/*
*//**
 * Gets whether you should show UI with rationale for requesting permissions.
 *
 * @param permissions The permissions your app wants to request.
 * @return Whether you can show permission rationale UI.
 *//*
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (FragmentCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    */

/**
 * Requests permissions needed for recording video.
 *//*
private void requestVideoPermissions() {
    if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {

        PermissionDialog.newInstance(
                getString(R.string.permission_request),
                VIDEO_PERMISSIONS,
                REQUEST_VIDEO_PERMISSIONS
        ).show(getChildFragmentManager(), FRAGMENT_DIALOG);

    } else {
        FragmentCompat.requestPermissions(this, VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
    }
}*/
public class PermissionDialog extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_PERMISSION = "permissions";
    private static final String ARG_REQUEST_CODE = "request_code";

    public static PermissionDialog newInstance(String message, String[] permissions, int requestCode) {

        Bundle args = new Bundle();

        args.putString(ARG_MESSAGE, message);
        args.putStringArray(ARG_PERMISSION, permissions);
        args.putInt(ARG_REQUEST_CODE, requestCode);

        PermissionDialog fragment = new PermissionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String message = getArguments().getString(ARG_MESSAGE);
        final String[] permissions = getArguments().getStringArray(ARG_PERMISSION);
        final int requestCode = getArguments().getInt(ARG_REQUEST_CODE);

        final Fragment parent = getParentFragment();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissions, requestCode);
                        } else {
                            FragmentCompat.requestPermissions(parent, permissions, requestCode);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                parent.getActivity().finish();
                            }
                        })
                .create();
        return alertDialog;
    }
}
