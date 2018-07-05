package com.windyroad.nghia.alarmtasks.helpers

import android.app.Activity
import android.content.Context
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.models.PeriodOfDay
import java.util.*


class ThemeHelper {

    companion object {

//    public static void changeToTheme(MainActivity activity, int theme) {
//        sTheme = theme;
//        activity.startActivity(new Intent(activity, MyActivity.class));
//    }

        /**
         * Set Theme đầu Activity
         */
        fun setActivityTheme(activity: Activity) {
            var intHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            when (TimeHelper.getPeriodOfDay(intHour)) {
                PeriodOfDay.MORNING -> activity.setTheme(R.style.AppTheme_Morning)
                PeriodOfDay.AFTERNOON -> activity.setTheme(R.style.AppTheme_Afternoon)
                PeriodOfDay.EVENING -> activity.setTheme(R.style.AppTheme_Evening)
                PeriodOfDay.NIGHT -> activity.setTheme(R.style.AppTheme_Night)
                PeriodOfDay.MIDNIGHT -> activity.setTheme(R.style.AppTheme_Midnight)
            }
        }

        fun getBackgroundColor(hour: Int) : Int {
            var resBg = R.color.app_bg_midnight
            when (TimeHelper.getPeriodOfDay(hour)) {
                PeriodOfDay.MORNING -> {
                    resBg = R.color.app_bg_morning
                }
                PeriodOfDay.AFTERNOON -> {
                    resBg = R.color.app_bg_afternoon
                }
                PeriodOfDay.EVENING -> {
                    resBg = R.color.app_bg_evening
                }
                PeriodOfDay.NIGHT -> {
                    resBg = R.color.app_bg_night
                }
                else -> {
                    resBg = R.color.app_bg_midnight
                }
            }
            return resBg
        }

        fun getImageBackgroundColor(hour: Int): Int {
            var resImgBackground = R.color.app_bg_image_midnight
            when (TimeHelper.getPeriodOfDay(hour)) {
                PeriodOfDay.MORNING -> {
                    resImgBackground = R.color.app_bg_image_morning
                }
                PeriodOfDay.AFTERNOON -> {
                    resImgBackground = R.color.app_bg_image_afternoon
                }
                PeriodOfDay.EVENING -> {
                    resImgBackground = R.color.app_bg_image_evening
                }
                PeriodOfDay.NIGHT -> {
                    resImgBackground = R.color.app_bg_image_night
                }
                else -> {
                    resImgBackground = R.color.app_bg_image_midnight
                }
            }
            return resImgBackground
        }

        fun getImageDrawable(hour: Int): Int {
            var resImage = R.drawable.bg_midnight
            when (TimeHelper.getPeriodOfDay(hour)) {
                PeriodOfDay.MORNING -> {
                    resImage = R.drawable.bg_morning
                }
                PeriodOfDay.AFTERNOON -> {
                    resImage = R.drawable.bg_afternoon
                }
                PeriodOfDay.EVENING -> {
                    resImage = R.drawable.bg_evening
                }
                PeriodOfDay.NIGHT -> {
                    resImage = R.drawable.bg_night
                }
                else -> {
                    resImage = R.drawable.bg_midnight
                }
            }
            return resImage
        }


        /**
         * Todo Not done yet
         */
        fun getColor(ctx: Context, theme: PeriodOfDay, colorName: String): Int {
            when (theme) {
                PeriodOfDay.MORNING -> return ctx.resources.getIdentifier("BLUE_$colorName", "color", ctx.packageName)
                PeriodOfDay.AFTERNOON -> return ctx.resources.getIdentifier("GREEN_$colorName", "color", ctx.packageName)
            }
            return ctx.resources.getIdentifier("BLUE_$colorName", "color", ctx.packageName)
        }
    }
}