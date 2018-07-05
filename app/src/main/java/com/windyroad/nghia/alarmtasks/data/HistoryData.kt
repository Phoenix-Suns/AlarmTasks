package com.windyroad.nghia.alarmtasks.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.windyroad.nghia.alarmtasks.models.History

/**
 * Created by ADMIN on 3/18/2018.
 */
class HistoryData(context: Context?) : BaseDataHandle(context) {
    companion object {

        // Table Code Detail
        val TABLE_HISTORY = "history"
        // Column Code Detail
        val KEY_HISTORY_TASK_NAME = "task_name"
        // Statement
        val HISTORY_CREATE_STATEMENT : String = "CREATE TABLE " + TABLE_HISTORY + "( " +
                CREATE_COMMON_COLUMNS + ", " +
                KEY_HISTORY_TASK_NAME + " TEXT" +
                ")"

        /**
         * Đổi History to ContentValues
         */
        private fun populateContent (model: History) : ContentValues {
            var values = BaseDataHandle.populateContent(model)

            values.put(KEY_HISTORY_TASK_NAME, model.taskName)

            return values
        }

        /**
         * Đổi ContentValue to History
         */
        private fun populateModel(model: History, c: Cursor){
            BaseDataHandle.populateModel(model, c)

            model.taskName = c.getString(c.getColumnIndex(KEY_HISTORY_TASK_NAME))
        }

        fun getById(context: Context, id: Long): History {
            var item = History()
            var db: SQLiteDatabase? = null
            val where = KEY_ID + "=" + id
            try {
                db  = BaseDataHandle(context).writableDatabase
                val cursor = db.query(TABLE_HISTORY, null, where, null, null, null, null)
                while (cursor.moveToNext()) {
                    populateModel(item, cursor)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                close(db)
            }
            return item
        }

        fun getAll(context: Context): ArrayList<History> {
            var listHistory = ArrayList<History>()

            var db: SQLiteDatabase? = null
            val query = "SELECT * FROM $TABLE_HISTORY";
            try {
                db  = BaseDataHandle(context).writableDatabase
                val cursor = db.rawQuery(query, null)
                while (cursor.moveToNext()) {
                    var item = History()
                    populateModel(item, cursor)
                    listHistory.add(item)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                close(db)
            }
            return listHistory
        }

        fun add(context: Context, model: History) : Long {
            val values = populateContent(model)
            return BaseDataHandle.insert(context, TABLE_HISTORY, values)
        }

        fun update(context: Context, model: History): Int {
            var values = populateContent(model)
            var where = "$KEY_ID = ${model.id}"
            return BaseDataHandle.update(context, TABLE_HISTORY, values, where)
        }

        fun delete(context: Context, historyId: Long) : Int {
            var where = "$KEY_ID = $historyId"
            return BaseDataHandle.delete(context, TABLE_HISTORY, where)
        }

        fun clearAll(context: Context): Int {
            return BaseDataHandle.delete(context, TABLE_HISTORY, null)

        }

    }
}