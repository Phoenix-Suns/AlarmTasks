package com.windyroad.nghia.alarmtasks.models

import android.net.Uri

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

    fun getRepeatingDay(dayOfWeek: Int): Boolean {
        return repeatingDays[dayOfWeek]
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
        val FRDIAY = 5
        val SATURDAY = 6

    }
}