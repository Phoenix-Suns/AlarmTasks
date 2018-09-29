package com.windyroad.nghia.alarmtasks.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper
import com.windyroad.nghia.alarmtasks.ui.AlarmScreenActivity

class ShowAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmIntent = Intent(context, AlarmScreenActivity::class.java)
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        alarmIntent.putExtras(intent)

        context.startActivity(alarmIntent)

        AlarmHelper.setAllAlarm(context)
    }
}
