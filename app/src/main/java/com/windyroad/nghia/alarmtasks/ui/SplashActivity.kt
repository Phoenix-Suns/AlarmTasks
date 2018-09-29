package com.windyroad.nghia.alarmtasks.ui

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.common.IntentUtil
import com.windyroad.nghia.common.activity.PermissionUtil
import com.windyroad.nghia.common.activity.PermissionUtil.REQUEST_ALL_PERMISSION
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    var permissions = arrayOf(
            Manifest.permission.VIBRATE,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE //if you can write, you can read
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Check Permission
        var remainingPermission = PermissionUtil.requestAllPermission(this, permissions, REQUEST_ALL_PERMISSION)
        if (remainingPermission.isEmpty()) {
            goMainActivity()
        }

        // Event
        button_Cancel.setOnClickListener(View.OnClickListener {
            goMainActivity()
        })
        button_Grant.setOnClickListener(View.OnClickListener {
            val i = IntentUtil.createOpenSettingIntent(this)
            startActivity(i)
        })
    }

    override fun onResume() {
        super.onResume()

        checkAndGoMainActivity()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        checkAndGoMainActivity()
    }


    private fun checkAndGoMainActivity() {
        // have permission not granted, don't go
        var notGranted = PermissionUtil.getPermissionNotGranted(this, permissions)
        if (notGranted.isEmpty())
            goMainActivity()
    }

    private fun goMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
