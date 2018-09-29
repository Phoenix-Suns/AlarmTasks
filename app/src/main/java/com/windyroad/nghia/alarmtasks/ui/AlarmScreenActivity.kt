package com.windyroad.nghia.alarmtasks.ui

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.PowerManager.WakeLock
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.data.HistoryData
import com.windyroad.nghia.alarmtasks.data.TaskData
import com.windyroad.nghia.alarmtasks.models.History
import kotlinx.android.synthetic.main.activity_alarm_screen.*
import android.media.AudioAttributes
import android.os.*
import android.support.v4.app.ActivityCompat
import com.windyroad.nghia.alarmtasks.helpers.ThemeHelper
import java.util.*
import android.os.VibrationEffect
import android.os.Build
import android.os.Vibrator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.KeyEvent
import com.windyroad.nghia.alarmtasks.data.MyAlarmData
import android.view.KeyEvent.KEYCODE_VOLUME_UP
import com.windyroad.nghia.alarmtasks.adapters.SmallTaskAdapter
import com.windyroad.nghia.alarmtasks.helpers.SwipeHelper


class AlarmScreenActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    lateinit var mListTask : ArrayList<String>
    lateinit var mTasksAdapter : SmallTaskAdapter

    private var mWakelock: WakeLock? = null
    private var mMedia: MediaPlayer? = null
    private var mAlarmId: Long? = null
    private var mVibrator: Vibrator? = null

    private val WAKELOCK_TIME: Long = 60 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setActivityTheme(this)
        setContentView(R.layout.activity_alarm_screen)

        initViews()

        setEvents()
    }

    private fun initViews() {
        mVibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Get Info
        mAlarmId = intent.extras.getLong(AlarmHelper.EXTRA_ID, -1)
        val name = intent.extras.getString(AlarmHelper.EXTRA_NAME)
        val timeHour = intent.extras.getInt(AlarmHelper.EXTRA_HOUR, 0)
        val timeMinute = intent.extras.getInt(AlarmHelper.EXTRA_MINUTE, 0)
        val tone = intent.extras.getString(AlarmHelper.EXTRA_TONE)
        val vibration = intent.extras.getBoolean(AlarmHelper.EXTRA_VIBRATION)

        // Update UI
        textView_Task.text = name
        var currTime = Calendar.getInstance()
        textView_Time.text = String.format("%02d:%02d", currTime.get(Calendar.HOUR_OF_DAY), currTime.get(Calendar.MINUTE))
        refreshListView()

        //--- Play Ringtone---
        playRingtone(tone)

        if (vibration)
            vibratePhone()

        // --- Sáng màn hình ---
        wakeUpDevice()
    }

    private fun playRingtone(tone: String?) {
        mMedia = MediaPlayer()
        try {
            if (tone != null && tone != "") {
                val toneUri = Uri.parse(tone)
                if (toneUri != null) {

                    mMedia?.setDataSource(this, toneUri)
                    mMedia?.isLooping = true

                    if (Build.VERSION.SDK_INT >= 21) {
                        val aa = AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                //.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // todo
                                .build()
                        mMedia?.setAudioAttributes(aa)
                    } else {
                        mMedia?.setAudioStreamType(AudioManager.STREAM_ALARM)
                    }

                    mMedia?.prepare()
                    mMedia?.start()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun vibratePhone() {

        val vibratePattern = longArrayOf(0, 500, 200, 500, 200, 500, 200, 1000)    //thời gian run: run,stop,run,stop,run,stop
        val repeatIndex = 0 //lặp lại từ vị trí 0 trong vibratePattern

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val amplitudes = intArrayOf(0, 255, 0, 255, 0, 255, 0, 255)    //cường độ run, 255 lớn nhất
            mVibrator?.vibrate(VibrationEffect.createWaveform(vibratePattern, amplitudes, repeatIndex))

        } else {
            //deprecated in API 26
            mVibrator?.vibrate(vibratePattern, repeatIndex)
        }
    }

    private fun wakeUpDevice() {
        val releaseWakelock = Runnable {

            window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            window.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)

            if (mWakelock != null && mWakelock!!.isHeld) {
                mWakelock!!.release()
            }
        }

        Handler().postDelayed(releaseWakelock, WAKELOCK_TIME)
    }

    private fun setEvents() {
        button_Dismiss.setOnClickListener {
            mMedia?.stop()
            ActivityCompat.finishAffinity(this);
        }

        /*mTasksAdapter.listener = object : SmallTaskAdapter.IListener {
            override fun onClick(view: View?, position: Int) {
                handleSelectTaskItem(position)
            }

            override fun onLongClick(view: View?, position: Int) {

            }

        }*/

        // Swipe to delete
        val itemTouchHelper = SwipeHelper(0, ItemTouchHelper.RIGHT, object : SwipeHelper.RecyclerItemTouchHelperListener{
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
                handleSelectTaskItem(position)
            }
        }, R.id.container)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(listView_Tasks)
    }

    private fun handleSelectTaskItem(position: Int) {
        // Save history
        val history = History(mListTask[position])
        HistoryData.add(baseContext, history)

        // Check turn off Alarm, not repeat => off
        var alarm = MyAlarmData.getById(baseContext, mAlarmId!!)
        if (!alarm.hasRepeat()) {
            MyAlarmData.setEnable(baseContext, mAlarmId!!, false)
            AlarmHelper.cancelAlarm(baseContext, mAlarmId!!)
        }

        // Stop Ringtone
        mMedia?.stop()
        mVibrator?.cancel()

        //Clear on Stack
        ActivityCompat.finishAffinity(this@AlarmScreenActivity)
    }

    private fun refreshListView() {
        listView_Tasks.setHasFixedSize(true)
        listView_Tasks.layoutManager = LinearLayoutManager(this);
        mListTask = TaskData.getAll(this)
        mTasksAdapter = SmallTaskAdapter(this, mListTask);
        listView_Tasks.adapter = mTasksAdapter
    }

    override fun onResume() {
        super.onResume()

        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)

        val pm = application.getSystemService(Context.POWER_SERVICE) as PowerManager

        if (mWakelock == null) {
            mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    or PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "ABC")
        }

        if (!mWakelock?.isHeld!!) {
            mWakelock?.acquire()
        }
    }

    override fun onPause() {
        super.onPause()

        if (mWakelock != null && mWakelock?.isHeld!!) {
            mWakelock?.release()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KEYCODE_VOLUME_UP) {
            // Stop Ringtone
            mMedia?.stop()
            mVibrator?.cancel()
        }
        return true
    }
}
