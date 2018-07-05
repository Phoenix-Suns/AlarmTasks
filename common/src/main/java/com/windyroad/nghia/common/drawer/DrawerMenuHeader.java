package com.windyroad.nghia.common.drawer;

/**
 * Created by Nghia-PC on 9/25/2015.
 * Drawer Header
 */
public class DrawerMenuHeader extends DrawerMenuItem {
    public DrawerMenuHeader(String title) {
        setType(Type.HEADER);

        this.setTitle(title);
    }
}
