package com.windyroad.nghia.alarmtasks.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.windyroad.nghia.alarmtasks.R;
import com.windyroad.nghia.common.models.FragmentPager;

import java.util.ArrayList;

/**
 * Created by Nghia-PC on 8/26/2015.
 * Adapter cho ViewPager Assign (hiển thị theo Page)
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private ArrayList<FragmentPager> mListPager;

    public MainPagerAdapter(FragmentManager fm, Context context, ArrayList<FragmentPager> listFragmentPager) {
        super(fm);
        this.mContext = context;

        // Mảng Tiêu đề, và Fragment Assign
        mListPager = listFragmentPager;
    }

    @Override
    public int getCount() {
        return mListPager.size();
    }

    /**
     * Trả về tiêu đề Liên quan Vị trí
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mListPager.get(position).getTitle();
    }

    /**
     * Trả về Fragment có Liên quan Vị trí
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        Fragment resultFrag = mListPager.get(position).getFragment();
        return resultFrag;
    }


    /**
     * Tạo custom View Header trả về, hiển thị
     * @param position
     * @return
     */
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.tab_header, null);

        TextView tv = (TextView) itemView.findViewById(R.id.textView);
        tv.setText(mListPager.get(position).getTitle());

        return itemView;
    }
}
