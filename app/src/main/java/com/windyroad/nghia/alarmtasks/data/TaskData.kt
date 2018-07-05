package com.windyroad.nghia.alarmtasks.data

import android.content.Context
import com.windyroad.nghia.alarmtasks.helpers.AppConfig
import com.windyroad.nghia.common.sqlite.DatabaseUtil

/**
 * Created by Nghia on 3/9/2018.
 */
class TaskData {
    companion object {
        fun add(context:Context, newTask : String) : Int {
            var resultID = DatabaseUtil.QUERY_FAIL

            var tasks = AppConfig.getTasks(context)

            if (tasks.isEmpty()) tasks = newTask //First Task
            else tasks += "|$newTask"

            AppConfig.setTasks(context, tasks)

            resultID = 1
            return resultID
        }


        fun update(context: Context, newList: List<String>): Int {
            var resultID = DatabaseUtil.QUERY_FAIL

            var tasks = newList.joinToString("|")
            AppConfig.setTasks(context, tasks)

            resultID = 1
            return resultID
        }

        fun getAll(context: Context) : ArrayList<String> {
            var listResult = ArrayList<String>()

            var tasks = AppConfig.getTasks(context)
            if (tasks != "")
                listResult = ArrayList<String>(tasks.split('|'))

            return listResult
        }
    }
}