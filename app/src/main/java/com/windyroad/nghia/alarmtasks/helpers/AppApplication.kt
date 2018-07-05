package com.windyroad.nghia.alarmtasks.helpers

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.windyroad.nghia.alarmtasks.data.MyAlarmData
import com.windyroad.nghia.alarmtasks.models.MyAlarm
import java.util.ArrayList

class AppApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        // --- Init First Data ---
        if (!AppConfig.getFirstInit(baseContext)) {
            // Fake data
            val ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(applicationContext, RingtoneManager.TYPE_RINGTONE)
            val alarm1 = MyAlarm(9, 0, BooleanArray(7), ringtoneUri, "", false)
            val alarm2 = MyAlarm(10, 0, BooleanArray(7), ringtoneUri, "", false)
            MyAlarmData.add(baseContext, alarm1)
            MyAlarmData.add(baseContext, alarm2)

            AppConfig.setTasks(baseContext, "Do Homework|Go to Mom Home")

            AppConfig.setFirstInit(baseContext, true)
        }
    }
}