package com.windyroad.nghia.common;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Imark-N on 10/8/2015.
 */
public class FragmentUtils {

    /** Đổi Fragment */
    public static void replaceFragment(FragmentActivity fragmentActivity, @IdRes int containerViewId, Fragment fragment, @Nullable String tag) {
        FragmentManager fragManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragTran = fragManager.beginTransaction();
        fragTran.replace(containerViewId, fragment, tag);

        if (tag != null) {
            // quay trở lại Fragment
            fragTran.addToBackStack(tag);
        }

        fragTran.commit();
    }
}
