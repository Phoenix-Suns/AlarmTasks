package com.windyroad.nghia.alarmtasks.views

import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.data.MyAlarmData
import com.windyroad.nghia.alarmtasks.helpers.ThemeHelper
import com.windyroad.nghia.alarmtasks.models.MyAlarm

import kotlinx.android.synthetic.main.activity_edit_alarm.*
import kotlinx.android.synthetic.main.content_edit_alarm.*
import java.util.*
import com.windyroad.nghia.alarmtasks.helpers.TimeHelper
import com.windyroad.nghia.alarmtasks.models.PeriodOfDay


class EditAlarmActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ALARM_ID = "alarm_id"
    }
    private val REQUEST_RINGTONE_PICKER = 1
    var DEFAULT_ALARM_ID = -1L //Flag Add MyAlarm

    private var mAlarmDetail: MyAlarm = MyAlarm()
    var mTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setActivityTheme(this)
        setContentView(R.layout.activity_edit_alarm)

        setEvents()
        initVars()
    }


    private fun initVars() {
        setSupportActionBar(toolbar)
        //supportActionBar?.setTitle(R.string.title_edit_alarm)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)


        var id =  intent.extras?.getLong(EXTRA_ALARM_ID, DEFAULT_ALARM_ID)
        if (id == DEFAULT_ALARM_ID || id == null) {
            // Add
            mAlarmDetail = MyAlarm()

            val currTime = Calendar.getInstance()
            updateAlarmUI(currTime.get(Calendar.HOUR_OF_DAY), currTime.get(Calendar.MINUTE))

            val ringUri = RingtoneManager.getActualDefaultRingtoneUri(applicationContext, RingtoneManager.TYPE_RINGTONE)
            textView_ToneSelection.text = RingtoneManager.getRingtone(this, ringUri).getTitle(this)

        } else {
            // Update
            mAlarmDetail = MyAlarmData.getById(baseContext, id!!)

            editText_AlarmName.setText(mAlarmDetail.name)
            toggle_RepeatMonday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.MONDAY)
            toggle_RepeatTuesday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.TUESDAY)
            toggle_RepeatWednesday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.WEDNESDAY)
            toggle_RepeatThursday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.THURSDAY)
            toggle_RepeatFriday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.FRDIAY)
            toggle_RepeatSaturday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.SATURDAY)
            toggle_RepeatSunday.isChecked = mAlarmDetail.getRepeatingDay(MyAlarm.SUNDAY)

            textView_ToneSelection.text = RingtoneManager.getRingtone(this, mAlarmDetail!!.alarmTone).getTitle(this)


            updateAlarmUI(mAlarmDetail.timeHour, mAlarmDetail.timeMinute)
        }
    }

    private fun setEvents() {
        //Set Ringtone
        container_Tone.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, mAlarmDetail.alarmTone)
            startActivityForResult(intent, REQUEST_RINGTONE_PICKER)
        }

        // Show Select time dialog
        textView_Time.setOnClickListener {
            TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(timePicker: TimePicker, hour: Int, minute: Int) {
                        updateAlarmUI(hour, minute)
                    }
                },
                mTime.get(Calendar.HOUR_OF_DAY),
                mTime.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Update Time distance
        toggle_RepeatMonday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }
        toggle_RepeatTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }
        toggle_RepeatWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }
        toggle_RepeatThursday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }
        toggle_RepeatFriday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }
        toggle_RepeatSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }
        toggle_RepeatSunday.setOnCheckedChangeListener { buttonView, isChecked ->
            updateAlarmUI(mTime.get(Calendar.HOUR_OF_DAY), mTime.get(Calendar.MINUTE))
        }


        fab_Save.setOnClickListener { view ->
            saveAlarm()
        }
    }

    private fun updateAlarmUI(hour: Int, minute: Int) {
        mTime.set(Calendar.HOUR_OF_DAY, hour)
        mTime.set(Calendar.MINUTE, minute)
        val hour = mTime.get(Calendar.HOUR_OF_DAY)
        textView_Time.text = TimeHelper.makeTime(hour, mTime.get(Calendar.MINUTE))

        updateModelFromLayout() // lấy lại mAlarmDetail
        textView_Distance.text = AlarmHelper.calDistanceTime(this, mAlarmDetail)

        // Day Period Background
        imageView_Period.setImageResource(ThemeHelper.getImageDrawable(hour))
        imageView_Period.setBackgroundResource(ThemeHelper.getImageBackgroundColor(hour))
        container.setBackgroundResource(ThemeHelper.getBackgroundColor(hour))

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.alarm_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // Close Activity
            android.R.id.home -> finish()

            // Save
            R.id.action_save_alarm -> {
                if (saveAlarm()) return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveAlarm(): Boolean {
        updateModelFromLayout()

        AlarmHelper.cancelAllAlarm(this);

        var result = -1L
        if (mAlarmDetail.id == DEFAULT_ALARM_ID) {
            // Add
            result = MyAlarmData.add(baseContext, mAlarmDetail)
        } else {
            // Update
            result = MyAlarmData.update(baseContext, mAlarmDetail).toLong()
        }

        if (result == -1L) {
            // Fail
            Toast.makeText(baseContext, R.string.message_fail, Toast.LENGTH_SHORT).show()
            return true
        }

        // Success
        AlarmHelper.setAllAlarm(this);
        setResult(RESULT_OK)
        finish()
        return false
    }


    private fun updateModelFromLayout() {

        mAlarmDetail.timeHour = mTime.get(Calendar.HOUR_OF_DAY)
        mAlarmDetail.timeMinute = mTime.get(Calendar.MINUTE)

        mAlarmDetail.name = editText_AlarmName.text.toString()

        mAlarmDetail.setRepeatingDay(MyAlarm.MONDAY, toggle_RepeatMonday.isChecked);
        mAlarmDetail.setRepeatingDay(MyAlarm.TUESDAY, toggle_RepeatTuesday.isChecked);
        mAlarmDetail.setRepeatingDay(MyAlarm.WEDNESDAY, toggle_RepeatWednesday.isChecked);
        mAlarmDetail.setRepeatingDay(MyAlarm.THURSDAY, toggle_RepeatThursday.isChecked);
        mAlarmDetail.setRepeatingDay(MyAlarm.FRDIAY, toggle_RepeatFriday.isChecked);
        mAlarmDetail.setRepeatingDay(MyAlarm.SATURDAY, toggle_RepeatSaturday.isChecked);
        mAlarmDetail.setRepeatingDay(MyAlarm.SUNDAY, toggle_RepeatSunday.isChecked)

        mAlarmDetail.isEnabled = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQUEST_RINGTONE_PICKER -> {
                        if (data != null) {

                            // SELECT ALARM
                            var tone = data.getParcelableExtra<Parcelable>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                            if (tone != null) {
                                mAlarmDetail.alarmTone = tone as Uri
                                textView_ToneSelection.text = RingtoneManager.getRingtone(this, mAlarmDetail.alarmTone).getTitle(this)
                            } else {
                                //Silent
                                mAlarmDetail.alarmTone = Uri.EMPTY
                                textView_ToneSelection.text = getString(R.string.message_silent)
                            }
                        }
                    }
                }
            }
        }
    }
}
