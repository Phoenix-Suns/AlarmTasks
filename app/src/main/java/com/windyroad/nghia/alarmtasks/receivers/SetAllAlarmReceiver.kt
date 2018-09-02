package com.windyroad.nghia.alarmtasks.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper

class SetAllAlarmReceiver : BroadcastReceiver() {

    private val TAG: String? = SetAllAlarmReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        Log.e(TAG, "onReceive")
        AlarmHelper.setAllAlarm(context)
    }
}
