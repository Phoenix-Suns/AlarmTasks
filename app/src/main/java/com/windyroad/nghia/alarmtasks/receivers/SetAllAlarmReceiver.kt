package com.windyroad.nghia.alarmtasks.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper

class SetAllAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        AlarmHelper.setAllAlarm(context)
    }
}
