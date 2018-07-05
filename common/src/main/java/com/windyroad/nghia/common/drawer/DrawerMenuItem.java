package com.windyroad.nghia.common.drawer;

/**
 * Created by Nghia-PC on 7/28/2015.
 * Navigation Drawer Item
 * Dùng cho DRAWER MENU
 */
public class DrawerMenuItem {

    private Type type;  // là header, nhóm
    private int iconResourceId;
    private String title;
    /** Hiện cảnh báo */
    private String notifyText;

    /**
     *Add Item
      */
    public DrawerMenuItem(int iconResourceId, String title, String notifyText) {
        setType(Type.ITEM);

        this.iconResourceId = iconResourceId;
        this.title = title;
        this.notifyText = notifyText;
    }

    public DrawerMenuItem() {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        DIVIDER, HEADER, ITEM;
        private static Type[] allValues = values();
        public static Type fromOrdinal(int n) {return allValues[n];}
    }
}
