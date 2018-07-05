package com.windyroad.nghia.common.models;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Nghia-PC on 8/28/2015.
 * Item dùng cho [Fragment Pager Adapter]
 * Gồm có Fragment + Title + ImageResourceId
 */
public class FragmentPager {
    private String title;
    private @Nullable Integer imageId;
    private Fragment fragment;

    public FragmentPager() {
    }

    public FragmentPager(String title, @Nullable Integer imageId, Fragment fragment) {
        this.title = title;
        this.imageId = imageId;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @Nullable Integer getImageId() {
        return imageId;
    }

    public void setImageId(@Nullable Integer imageId) {
        this.imageId = imageId;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
