package com.windyroad.nghia.alarmtasks.helpers

import com.windyroad.nghia.alarmtasks.models.PeriodOfDay

object TimeHelper {
    fun makeTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }

    fun makeDate(year: Int, month: Int, day: Int): String {
        return String.format("%04d-%02d-%02d", year, month, day)
    }

    fun getPeriodOfDay(hour: Int) : PeriodOfDay {
        return when (hour) {
            in 0..5 -> PeriodOfDay.MIDNIGHT
            in 5..11 -> PeriodOfDay.MORNING
            in 11..17 -> PeriodOfDay.AFTERNOON
            in 17..19 -> PeriodOfDay.EVENING
            in 19..23 -> PeriodOfDay.NIGHT
            else -> PeriodOfDay.MIDNIGHT
        }
    }
}