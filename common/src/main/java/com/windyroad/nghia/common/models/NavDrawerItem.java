package com.windyroad.nghia.common.models;

/**
 * Created by Nghia-PC on 7/28/2015.
 * Navigation Drawer Item
 * Dùng cho DRAWER MENU
 */
public class NavDrawerItem {
    private int iconResourceId;
    private String title;
    /** Hiện cảnh báo */
    private String notifyText;

    public NavDrawerItem() {
    }

    public NavDrawerItem(int iconResourceId, String title, String notifyText) {
        this.iconResourceId = iconResourceId;
        this.title = title;
        this.notifyText = notifyText;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotifyText() {
        return notifyText;
    }

    public void setNotifyText(String notifyText) {
        this.notifyText = notifyText;
    }
}
