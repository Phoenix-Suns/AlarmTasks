package com.windyroad.nghia.alarmtasks.helpers

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent
import android.content.Context;
import android.content.Intent;
import android.os.Build
import android.os.Bundle
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.data.MyAlarmData
import com.windyroad.nghia.alarmtasks.models.MyAlarm
import com.windyroad.nghia.alarmtasks.receivers.ShowAlarmReceiver
import com.windyroad.nghia.common.CalendarUtil


/**
 * Created by Nghia on 3/6/2018.
 */
class AlarmHelper {

    internal var TAG = this.javaClass.simpleName

    companion object {
        val EXTRA_ID = "extra_id"
        val EXTRA_NAME = "extra_name"
        val EXTRA_HOUR = "extra_timeHour"
        val EXTRA_MINUTE = "extra_timeMinute"
        val EXTRA_TONE = "extra_alarmTone"

        fun setAllAlarm(context: Context) {
            cancelAllAlarm(context)

            val alarms = MyAlarmData.getAll(context)

            for (alarm in alarms) {
                if (alarm.isEnabled) {
                    setAlarm(context, alarm)
                }
            }
        }

        fun setAlarm(context: Context, alarm: MyAlarm) {

            val pIntent = createIntentWithExtra(context, alarm)

            /* ---------- Set Direct Alarm  -----------

            val calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, alarm.timeHour)
            calendar.set(Calendar.MINUTE, alarm.timeMinute)
            calendar.set(Calendar.SECOND, 0)

            val nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            val nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val nowMinute = Calendar.getInstance().get(Calendar.MINUTE)
            var alarmSet = false


            if ((alarm.timeHour > nowHour) or
                    (alarm.timeHour == nowHour && alarm.timeMinute > nowMinute)) {

                // Chưa tới thời gian (giờ phút) => hẹn giờ
                calendar.set(Calendar.DAY_OF_WEEK, nowDay)
                setAlarm(context, calendar, pIntent)
                alarmSet = true

            } else {
                // Thời gian hiện tại đã qua thời gian Hẹn giờ
                // Chạy hết ngày trong tuần
                for (dayOfWeek in Calendar.SUNDAY..Calendar.SATURDAY) {
                    if (alarm.getRepeatingDay(dayOfWeek - 1)
                            && nowDay <= dayOfWeek
                            && !(dayOfWeek == nowDay && alarm.timeHour < nowHour)
                            && !(dayOfWeek == nowDay && alarm.timeHour == nowHour && alarm.timeMinute <= nowMinute)) {
                        //lấy ngày lặp + Hôm nay đã qua ngày lặp +
                        //Không: Hôm nay là ngày lặp + Giờ phút đã qua

                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
                        Log.i("abc123", "set Alarmed: " + nowDay + " : " + alarm.timeHour + " : " + alarm.timeMinute)

                        setAlarm(context, calendar, pIntent)
                        alarmSet = true
                        break
                    }
                }
            }

            if (!alarmSet) {
                for (dayOfWeek in Calendar.SUNDAY..Calendar.SATURDAY) {
                    if (alarm.getRepeatingDay(dayOfWeek - 1) && nowDay >= dayOfWeek) {
                        //lấy ngày lặp + Hôm nay chưa đến ngày lặp

                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
                        calendar.add(Calendar.WEEK_OF_YEAR, 1)

                        setAlarm(context, calendar, pIntent)
                        break
                    }
                }
            }*/

            var calTime = calculateTime(alarm)
            setAlarm(context, calTime, pIntent)

        }


        fun setAlarm(context: Context, calendar: Calendar, pIntent: PendingIntent) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pIntent)
            } else {
                val info = AlarmManager.AlarmClockInfo(calendar.timeInMillis, pIntent)  //Show Clock Icon
                alarmManager.setAlarmClock(info, pIntent)
            }
        }


        fun cancelAllAlarm(context: Context) {
            val alarms = MyAlarmData.getAll(context)

            for (alarm in alarms) {
                if (alarm.isEnabled) {
                    cancelAlarm(context, alarm.id)
                }
            }
        }

        fun cancelAlarm(context: Context, alarmId: Long) {
            val intent = Intent(context, ShowAlarmReceiver::class.java)
            val pIntent = PendingIntent.getBroadcast(context, alarmId.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pIntent)
        }

        private fun createIntentWithExtra(context: Context, model: MyAlarm): PendingIntent {
            val intent = Intent(context, ShowAlarmReceiver::class.java)

            var bundle = Bundle()
            bundle.putLong(EXTRA_ID, model.id)
            bundle.putString(EXTRA_NAME, model.name)
            bundle.putInt(EXTRA_HOUR, model.timeHour)
            bundle.putInt(EXTRA_MINUTE, model.timeMinute)
            bundle.putString(EXTRA_TONE, model.alarmTone.toString())
            intent.putExtras(bundle)

            return PendingIntent.getBroadcast(context, model.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        /**
         * Tính thời gian Alarm so với Hiện tại
         */
        fun calculateTime(alarm: MyAlarm): Calendar {
            val calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, alarm.timeHour)
            calendar.set(Calendar.MINUTE, alarm.timeMinute)
            calendar.set(Calendar.SECOND, 0)

            val nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            val nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val nowMinute = Calendar.getInstance().get(Calendar.MINUTE)
            var timeSet = false  // had Set Time

            if (!alarm.hasRepeat()){
                // Không có lặp ngày nào -> Báo thức theo giờ
                calendar.set(Calendar.DAY_OF_WEEK, nowDay)

                if ((alarm.timeHour > nowHour) or (alarm.timeHour == nowHour && alarm.timeMinute > nowMinute)) {
                    // Chưa tới thời gian (giờ phút) => hẹn giờ
                }
                else {
                    // Qua thời gian Báo thức - Báo thức ngày mai
                    calendar.add(Calendar.DATE, 1)
                }

                timeSet = true
            }
            /*
            if ((alarm.timeHour > nowHour) or (alarm.timeHour == nowHour && alarm.timeMinute > nowMinute)) {
                // Chưa tới thời gian (giờ phút) => hẹn giờ

                calendar.set(Calendar.DAY_OF_WEEK, nowDay)
                timeSet = true

            }*/
            else {
                // Hẹn giờ theo Repeat

                // Thời gian hiện tại đã qua thời gian Hẹn giờ
                // Chạy hết ngày trong tuần
                for (dayOfWeek in Calendar.SUNDAY..Calendar.SATURDAY) {
                    if (alarm.getRepeatingDay(dayOfWeek - 1)
                            && nowDay <= dayOfWeek
                            && !(dayOfWeek == nowDay && alarm.timeHour < nowHour)
                            && !(dayOfWeek == nowDay && alarm.timeHour == nowHour && alarm.timeMinute <= nowMinute)) {
                        //lấy ngày lặp + Hôm nay đã qua ngày lặp +
                        //Không: Hôm nay là ngày lặp + Giờ phút đã qua

                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
                        timeSet = true
                        break
                    }
                }
            }

            if (!timeSet) {
                for (dayOfWeek in Calendar.SUNDAY..Calendar.SATURDAY) {
                    if (alarm.getRepeatingDay(dayOfWeek - 1) && nowDay >= dayOfWeek) {
                        //lấy ngày lặp + Hôm nay chưa đến ngày lặp

                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
                        calendar.add(Calendar.WEEK_OF_YEAR, 1)
                        break
                    }
                }
            }

            return calendar
        }

        /**
         * Trừ thời gian Hiện tại
         */
        fun calDistanceTime(context: Context, item: MyAlarm): StringBuilder {
            var diffMilis = CalendarUtil.minusTime(calculateTime(item), Calendar.getInstance())
            var diffMap = CalendarUtil.calculateTime(diffMilis)

            var sb = StringBuilder()
            if (diffMap.get(Calendar.DAY_OF_YEAR) != 0) {
                sb.append(context.getString(R.string.days, diffMap.get(Calendar.DAY_OF_YEAR)) + " ")
            }
            if (diffMap.get(Calendar.HOUR_OF_DAY) != 0) {
                sb.append(context.getString(R.string.hours, diffMap.get(Calendar.HOUR_OF_DAY)) + " ")
            }
            if (diffMap.get(Calendar.MINUTE) != 0) {
                sb.append(context.getString(R.string.minutes, diffMap.get(Calendar.MINUTE)))
            }
            return sb
        }
    }
}
