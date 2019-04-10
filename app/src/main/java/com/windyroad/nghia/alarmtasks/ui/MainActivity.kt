package com.windyroad.nghia.alarmtasks.ui

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.adapters.MainPagerAdapter
import com.windyroad.nghia.alarmtasks.presenters.BasePresenter
import com.windyroad.nghia.common.models.FragmentPager

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.windyroad.nghia.alarmtasks.helpers.ThemeHelper
import com.windyroad.nghia.alarmtasks.helpers.TimeHelper
import com.windyroad.nghia.alarmtasks.models.PeriodOfDay
import java.util.*
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TabHost
import android.widget.TextView
import android.widget.Toast
import com.windyroad.nghia.common.AnimationUtil
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), BasePresenter.BaseView {


    private lateinit var mMainPagerAdapter: MainPagerAdapter
    private lateinit var mAlarmsFrag: ListAlarmFragment
    private lateinit var mTasksFrag: ListTaskFragment
    private lateinit var mHistoryFrag: ListHistoryFragment

    internal var isShowTitle = true

    private val mTimeChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Intent.ACTION_TIME_CHANGED) {
                updateUITime()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setActivityTheme(this)
        setContentView(R.layout.activity_main)

        initTab()
        setupVars()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mTimeChangedReceiver)
    }

    private fun setupVars() {

        setSupportActionBar(toolbar)

        // Listen Time Change
        var intent = IntentFilter()
        intent.addAction(Intent.ACTION_TIME_CHANGED)
        registerReceiver(mTimeChangedReceiver, intent)

        // Show Title when finish Scroll
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset == 0) {
                    updateUITime()
                    isShowTitle = true
                } else if (isShowTitle) {
                    isShowTitle = false
                    updateUITime()
                }
            }
        })
    }


    //region Hỗ trợ
    private fun initTab() {
        // Init List Fragment Pager
        mAlarmsFrag = ListAlarmFragment.newInstance()
        mTasksFrag = ListTaskFragment.newInstance()
        mHistoryFrag = ListHistoryFragment.newInstance()

        val listFragmentPager = object : ArrayList<FragmentPager>() {
            init {
                add(FragmentPager(getString(R.string.title_list_alarm), 0, mAlarmsFrag))
                add(FragmentPager(getString(R.string.title_list_task), 0, mTasksFrag))
                add(FragmentPager(getString(R.string.title_list_history), 0, mHistoryFrag))
            }
        }

        //Inflate View, find Views
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout_Main)  // header
        val viewPager = findViewById<ViewPager>(R.id.viewPager_Main)

        // Đặt Adapter cho View Pager
        mMainPagerAdapter = MainPagerAdapter(supportFragmentManager, this, listFragmentPager)
        viewPager.adapter = mMainPagerAdapter
        tabLayout.setupWithViewPager(viewPager)  // Đồng bộ TabLayout và ViewPager

        // Set Custom Header All Tabs
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            tab?.customView = mMainPagerAdapter.getTabView(i)
        }
    }
    //endregion

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            /*R.id.action_add_new_alarm -> startAlarmDetailActivity(-1)
            R.id.action_list_alarm -> startListAlarmActivity()*/
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUITime() {
        var curTime = Calendar.getInstance()
        var intHour = curTime.get(Calendar.HOUR_OF_DAY)
        var strTime = TimeHelper.makeTime(intHour, curTime.get(Calendar.MINUTE))
        textView_Time.text = strTime

        if (isShowTitle) {
            toolbar.title = strTime
        } else {
            toolbar.title = " "
        }

        // Day Period Background
        var bg = when (TimeHelper.getPeriodOfDay(intHour)) {
            PeriodOfDay.MORNING -> R.drawable.bg_morning_small
            PeriodOfDay.AFTERNOON -> R.drawable.bg_afternoon_small
            PeriodOfDay.EVENING -> R.drawable.bg_evening_small
            PeriodOfDay.NIGHT -> R.drawable.bg_night_small
            else -> R.drawable.bg_midnight
        }
        imageView_Period.setImageResource(bg)

    }

}
