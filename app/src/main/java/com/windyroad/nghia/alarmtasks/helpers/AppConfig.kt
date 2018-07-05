package com.windyroad.nghia.alarmtasks.helpers

import android.content.Context
import android.content.PeriodicSync
import android.content.SharedPreferences
import android.os.Environment
import com.windyroad.nghia.alarmtasks.models.PeriodOfDay
import com.windyroad.nghia.common.file.FileUtil

/**
 * Created by Nghia-PC on 8/7/2015.
 * Thông tin cài đặt Ứng dụng
 */
object AppConfig {

    private const val NAME_APP_CONFIG = "app_config"

    /** Thư mục chứa dữ liệu ứng dụng  */
    val APP_DIRECTORY_PATH = "Alarm Task"


    /** Cài đặt Ứng Dụng  */
    private const val KEY_TASKS = "tasks"
    private const val KEY_FIRST_INIT = "first_run"
    private const val KEY_EXPORT_PATH = ""

    fun setTasks(context: Context, tasks: String) {
        val sp = context.getSharedPreferences(AppConfig.NAME_APP_CONFIG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(AppConfig.KEY_TASKS, tasks)
        editor.apply()
    }

    fun getTasks(context: Context): String {
        val sp = context.getSharedPreferences(NAME_APP_CONFIG, Context.MODE_PRIVATE)
        return sp.getString(KEY_TASKS, "")
    }

    fun setFirstInit(context: Context, firstRun: Boolean) {
        val sp = context.getSharedPreferences(AppConfig.NAME_APP_CONFIG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(AppConfig.KEY_FIRST_INIT, firstRun)
        editor.apply()
    }

    fun getFirstInit(context: Context): Boolean {
        val sp = context.getSharedPreferences(NAME_APP_CONFIG, Context.MODE_PRIVATE)
        return sp.getBoolean(KEY_FIRST_INIT, false)
    }

    /**
     * Xóa thông tin App
     */
    fun clear(context: Context) {
        context.getSharedPreferences(NAME_APP_CONFIG, Context.MODE_PRIVATE)
                .edit().clear().apply()
    }

    /**
     * Tạo đường dẫn file History
     */
    fun makeExportHistoryFilePath(context: Context): String? {
        var resultPath = FileUtil.makeFilePathByTime(Environment.getExternalStorageDirectory().path, "History", "csv")

        val sp = context.getSharedPreferences(NAME_APP_CONFIG, Context.MODE_PRIVATE)
        val strPath = sp.getString(KEY_EXPORT_PATH, "")

        // Export File Trong cài đặt
        if (strPath != "") {
            resultPath = FileUtil.makeFilePathByTime(strPath, "History", "csv")
        }

        return resultPath
    }

}
