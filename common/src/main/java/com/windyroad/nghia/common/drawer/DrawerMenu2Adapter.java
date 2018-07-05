package com.windyroad.nghia.common.drawer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.windyroad.nghia.common.R;
import com.windyroad.nghia.common.models.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by Imark-N on 12/4/2015.
 */
public class DrawerMenu2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<DrawerMenuItem> mDrawerMenuItems;
    private OnItemClickListener mListener;

    public DrawerMenu2Adapter(Context context, ArrayList<DrawerMenuItem> menuItems) {
        this.mContext = context;
        this.mDrawerMenuItems = menuItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //http://stackoverflow.com/questions/26530685/is-there-an-addheaderview-equivalent-for-recyclerview
        //http://stackoverflow.com/questions/26649406/nested-recycler-view-height-doesnt-wrap-its-content

        // khởi tạo giao diện
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // tìm ViewHolder từng loại
        DrawerMenuItem.Type type = DrawerMenuItem.Type.fromOrdinal(viewType);
        switch (type) {
            case ITEM:
                View itemView = inflater.inflate(R.layout.list_item_drawer_menu, parent, false);
                return new ItemViewHolder(itemView);
            case DIVIDER:
                itemView = inflater.inflate(R.layout.list_item_drawer_menu_divider, parent, false);
                return new DividerViewHolder(itemView);
            case HEADER:
                itemView = inflater.inflate(R.layout.list_item_drawer_menu_header, parent, false);
                return new HeaderViewHolder(itemView);
        }
        throw new RuntimeException("Not found format");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DrawerMenuItem menuItem = mDrawerMenuItems.get(position);

        //--- set value ---
        // kiểm tra loại ViewHolder
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.textView_Title.setText(menuItem.getTitle());
            itemViewHolder.textView_Notify.setText(menuItem.getNotifyText());
            Drawable icon = ContextCompat.getDrawable(mContext, menuItem.getIconResourceId());
            itemViewHolder.imageView_Icon.setImageDrawable(icon);

        } else if (holder instanceof DividerViewHolder) {

        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.textView_Title.setText(menuItem.getTitle());
            headerViewHolder.textView_Notify.setText(menuItem.getNotifyText());
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerMenuItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        //Get the type of View that will be created
        // Cần cho nhiều layout khác nhau
        DrawerMenuItem item = mDrawerMenuItems.get(position);
        return item.getType().ordinal();  //cast enum to int
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * Divider ViewHolder, xml file: list_item_drawer_menu_divider
     */
    class DividerViewHolder extends RecyclerView.ViewHolder {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Header ViewHolder, xml file: list_item_drawer_menu_header
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView textView_Title;
        TextView textView_Notify;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            this.textView_Title = (TextView) itemView.findViewById(R.id.listItem_textView_Title);
            this.textView_Notify = (TextView) itemView.findViewById(R.id.listItem_textView_notify);
        }
    }

    /**
     * Item ViewHolder, xml file: list_item_drawer_menu
     */
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_Title;
        TextView textView_Notify;
        ImageView imageView_Icon;

        private final View mParentView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            this.mParentView = itemView;

            // find view
            this.textView_Title = (TextView) itemView.findViewById(R.id.listItem_textView_Title);
            this.textView_Notify = (TextView) itemView.findViewById(R.id.listItem_textView_notify);
            this.imageView_Icon = (ImageView) itemView.findViewById(R.id.listItem_imageView_Icon);

            // init event
            // click item
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(mParentView, this.getAdapterPosition());
            }
        }
    }
}
