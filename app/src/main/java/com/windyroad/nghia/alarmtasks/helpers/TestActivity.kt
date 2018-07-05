package com.windyroad.nghia.alarmtasks.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button

import com.windyroad.nghia.alarmtasks.R
import kotlinx.android.synthetic.main.content_test.*
import java.io.File
import java.io.FileInputStream


class TestActivity internal constructor() : AppCompatActivity() {

    private val REQUEST_RINGTONE_PICKER = 1
    var mToneUri : Uri = Uri.EMPTY
    private lateinit var mMedia: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        button_Get.setOnClickListener { view ->
            // Open get
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, mToneUri)
            startActivityForResult(intent, REQUEST_RINGTONE_PICKER)
        }

        button_Play.setOnClickListener{view ->
            playRingtone(mToneUri.toString())
        }

        button_Stop.setOnClickListener{view ->
            mMedia?.stop()
        }
    }



    private fun playRingtone(tone: String?) {
        mMedia = MediaPlayer()
        try {
            if (tone != null && tone != "") {
                val toneUri = Uri.parse(tone)
                if (toneUri != null) {

                    //var fis = FileInputStream(tone)
                    //mMedia.setDataSource(fis.fd)
                    mMedia?.setDataSource(this, toneUri)
                    //setMediaPlayerDataSource(this, mMedia!!, tone)

                    mMedia?.isLooping = true

                    if (Build.VERSION.SDK_INT >= 21) {
                        val aa = AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
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
                                mToneUri = tone as Uri
                                button_Get.text = RingtoneManager.getRingtone(this, mToneUri).getTitle(this)
                            } else {

                                //Silent
                                mToneUri = Uri.EMPTY
                                button_Get.text = getString(R.string.message_silent)
                            }
                        }
                    }
                }
            }
        }
    }


    //region đống thần thánh
    @Throws(Exception::class)
    fun setMediaPlayerDataSource(context: Context, mp: MediaPlayer, fileInfo: String) {
        var fileInfo = fileInfo

        if (fileInfo.startsWith("content://")) {
            try {
                val uri = Uri.parse(fileInfo)
                fileInfo = getRingtonePathFromContentUri(context, uri)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
                try {
                    setMediaPlayerDataSourcePreHoneyComb(context, mp, fileInfo)
                } catch (e: Exception) {
                    setMediaPlayerDataSourcePostHoneyComb(context, mp, fileInfo)
                }
            else
                setMediaPlayerDataSourcePostHoneyComb(context, mp, fileInfo)

        } catch (e: Exception) {
            try {
                setMediaPlayerDataSourceUsingFileDescriptor(context, mp,
                        fileInfo)
            } catch (ee: Exception) {
                val uri = getRingtoneUriFromPath(context, fileInfo)
                mp.reset()
                mp.setDataSource(uri)
            }

        }
    }

    @Throws(Exception::class)
    private fun setMediaPlayerDataSourcePreHoneyComb(context: Context, mp: MediaPlayer, fileInfo: String) {
        mp.reset()
        mp.setDataSource(fileInfo)
    }

    @Throws(Exception::class)
    private fun setMediaPlayerDataSourcePostHoneyComb(context: Context, mp: MediaPlayer, fileInfo: String) {
        mp.reset()
        mp.setDataSource(context, Uri.parse(Uri.encode(fileInfo)))
    }

    @Throws(Exception::class)
    private fun setMediaPlayerDataSourceUsingFileDescriptor(context: Context, mp: MediaPlayer, fileInfo: String) {
        val file = File(fileInfo)
        val inputStream = FileInputStream(file)
        mp.reset()
        mp.setDataSource(inputStream.getFD())
        inputStream.close()
    }

    private fun getRingtoneUriFromPath(context: Context, path: String): String {
        val ringtoneUri = MediaStore.Audio.Media.getContentUriForPath(path)
        val ringtoneCursor = context.contentResolver.query(
                ringtoneUri, null,
                MediaStore.Audio.Media.DATA + "='" + path + "'", null, null)
        ringtoneCursor!!.moveToFirst()

        val id = ringtoneCursor.getLong(ringtoneCursor
                .getColumnIndex(MediaStore.Audio.Media._ID))
        ringtoneCursor.close()

        return if (!ringtoneUri.toString().endsWith(id.toString())) {
            ringtoneUri.toString() + "/" + id
        } else ringtoneUri.toString()
    }

    fun getRingtonePathFromContentUri(context: Context, contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val ringtoneCursor = context.contentResolver.query(contentUri,
                proj, null, null, null)
        ringtoneCursor!!.moveToFirst()

        val path = ringtoneCursor.getString(ringtoneCursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))

        ringtoneCursor.close()
        return path
    }
    //endregion
}
