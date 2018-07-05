package com.windyroad.nghia.common.drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.windyroad.nghia.common.R;

import java.util.ArrayList;

/**
 * Created by Nghia-PC on 9/24/2015.
 */
public class DrawerMenuAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DrawerMenuItem> mDrawerMenuItems;

    public DrawerMenuAdapter(Context context, ArrayList<DrawerMenuItem> drawerMenuItems) {
        this.mContext = context;
        this.mDrawerMenuItems = drawerMenuItems;
    }

    @Override
    public int getCount() {
        return mDrawerMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawerMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        //Get the type of View that will be created
        // Cần cho nhiều layout khác nhau
        DrawerMenuItem item = mDrawerMenuItems.get(position);
        return item.getType().ordinal();  //cast enum to int
    }

    @Override
    public int getViewTypeCount() {
        // Returns the number of types of Views that will be created
        // Cần cho nhiều layout khác nhau
        return DrawerMenuItem.Type.values().length;  // get enum length
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerMenuItem item = mDrawerMenuItems.get(position);
        DrawerMenuViewHolder viewHolder = null;

        // --- init ---
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            switch (item.getType()){
                case ITEM:
                    // item layout
                    convertView = inflater.inflate(R.layout.list_item_drawer_menu, null);
                    viewHolder = new DrawerMenuViewHolder(convertView); // find lại
                    break;
                case HEADER:
                    // header layout
                    convertView = inflater.inflate(R.layout.list_item_drawer_menu_header, null);
                    viewHolder = new DrawerMenuViewHolder(convertView); // find lại
                    break;
                case DIVIDER:
                    // divider layout
                    convertView = inflater.inflate(R.layout.list_item_drawer_menu_divider, null);
                    viewHolder = new DrawerMenuViewHolder(convertView);  // find lại
                    break;
                default:
                    // item layout
                    convertView = inflater.inflate(R.layout.list_item_drawer_menu, null);
                    viewHolder = new DrawerMenuViewHolder(convertView); // find lại
                    break;
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DrawerMenuViewHolder) convertView.getTag();
        }

        //--- set value ---
        if (item.getType() != DrawerMenuItem.Type.DIVIDER) {
            // bỏ qua Divider
            viewHolder.setTitle(item.getTitle());
            viewHolder.setNotify(item.getNotifyText());
            viewHolder.setIcon(item.getIconResourceId());
        }

        return convertView;
    }

    /**
     * Xử lý nhanh View
     */
    public class DrawerMenuViewHolder {
        TextView textView_Title;
        TextView textView_Notify;
        ImageView imageView_Icon;

        public DrawerMenuViewHolder(View rootView) {
            this.textView_Title = (TextView) rootView.findViewById(R.id.listItem_textView_Title);
            this.textView_Notify = (TextView) rootView.findViewById(R.id.listItem_textView_notify);
            this.imageView_Icon = (ImageView) rootView.findViewById(R.id.listItem_imageView_Icon);
        }

        public void setTitle(String title) {
            if (textView_Title != null) {
                textView_Title.setText(title);
            }
        }

        public void setNotify(String notify) {
            if (textView_Notify != null) {
                textView_Notify.setText(notify);
            }
        }

        public void setIcon(int iconId) {
            if (this.imageView_Icon != null && iconId != 0) {
                Drawable icon = ContextCompat.getDrawable(mContext, iconId);
                this.imageView_Icon.setImageDrawable(icon);
            }
        }
    }
}
