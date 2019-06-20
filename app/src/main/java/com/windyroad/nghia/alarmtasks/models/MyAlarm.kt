package com.windyroad.nghia.alarmtasks.models

import android.net.Uri
import java.util.*

/**
 * Created by Nghia on 2/15/2018.
 */
class MyAlarm : BaseDataObject {

    var timeHour: Int = 0
    var timeMinute: Int = 0
    var repeatingDays: BooleanArray
    var alarmTone: Uri?
    var name: String= ""
    var isVibration: Boolean = true
    var isEnabled: Boolean = false

    constructor() {
        repeatingDays = BooleanArray(7)
        alarmTone = Uri.EMPTY
    }

    constructor(timeHour: Int, timeMinute: Int, repeatingDays: BooleanArray, alarmTone: Uri?,
                name: String, isVibration: Boolean, isEnabled: Boolean) {
        this.timeHour = timeHour
        this.timeMinute = timeMinute
        this.repeatingDays = repeatingDays
        this.alarmTone = alarmTone
        this.name = name
        this.isVibration = isVibration
        this.isEnabled = isEnabled
    }


    fun setRepeatingDay(dayOfWeek: Int, value: Boolean) {
        repeatingDays[dayOfWeek] = value
    }

    fun isRepeatingDay(dayOfWeek: Int): Boolean {
        return repeatingDays[dayOfWeek]
    }

    fun isRepeatingDayFromCalendar(calendarDayOfWeek: Int): Boolean {
        val dayOfWeek = dayOfWeekFromCalendar(calendarDayOfWeek)
        return isRepeatingDay(dayOfWeek)
    }

    fun hasRepeat(): Boolean {
        for (day in repeatingDays) {
            if (day) {
                return true
            }
        }
        return false
    }

    companion object {
        val SUNDAY = 0
        val MONDAY = 1
        val TUESDAY = 2
        val WEDNESDAY = 3
        val THURSDAY = 4
        val FRIDAY = 5
        val SATURDAY = 6

        fun dayOfWeekFromCalendar(calendarDayOfWeek: Int): Int = when(calendarDayOfWeek) {
            Calendar.SUNDAY -> SUNDAY
            Calendar.MONDAY -> MONDAY
            Calendar.TUESDAY -> TUESDAY
            Calendar.WEDNESDAY -> WEDNESDAY
            Calendar.THURSDAY -> THURSDAY
            Calendar.FRIDAY -> FRIDAY
            Calendar.SATURDAY -> SATURDAY
            else -> SUNDAY
        }
    }
}