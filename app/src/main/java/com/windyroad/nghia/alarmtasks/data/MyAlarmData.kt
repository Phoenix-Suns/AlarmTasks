package com.windyroad.nghia.alarmtasks.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.windyroad.nghia.alarmtasks.models.MyAlarm
import android.database.Cursor
import android.net.Uri


/**
 * Created by Nghia on 2/17/2018.
 */
class MyAlarmData(context: Context?) : BaseDataHandle(context) {

    companion object {

        // Table Code Detail
        val TABLE_ALARM = "my_alarm"
        // Column Code Detail
        val KEY_ALARM_NAME = "name"
        val KEY_ALARM_HOUR = "hour"
        val KEY_ALARM_MINUTE = "minute"
        val KEY_ALARM_REPEATE_DAYS = "repeat_days"
        val KEY_ALARM_TONE = "tone"
        val KEY_ALARM_VIBRATION = "vibration"
        val KEY_ALARM_ENABLED = "enabled"
        // Statement
        val MY_ALARM_CREATE_STATEMENT : String = "CREATE TABLE " + TABLE_ALARM + "( " +
                CREATE_COMMON_COLUMNS + ", " +
                KEY_ALARM_NAME + " TEXT, " +
                KEY_ALARM_HOUR + " INTEGER, " +
                KEY_ALARM_MINUTE + " INTEGER, " +
                KEY_ALARM_REPEATE_DAYS + " TEXT, " +
                KEY_ALARM_TONE + " TEXT, " +
                KEY_ALARM_VIBRATION + " BOOLEAN, " +
                KEY_ALARM_ENABLED + " BOOLEAN " +
                ")"

        /**
         * Đổi MyAlarm to ContentValues
         */
        private fun populateContent (model: MyAlarm) : ContentValues {
            var values = BaseDataHandle.populateContent(model)

            values.put(KEY_ALARM_NAME, model.name)
            values.put(KEY_ALARM_HOUR, model.timeHour)
            values.put(KEY_ALARM_MINUTE, model.timeMinute)
            values.put(KEY_ALARM_TONE, if (model.alarmTone != null) model.alarmTone.toString() else "")
            values.put(KEY_ALARM_VIBRATION, model.isVibration)
            values.put(KEY_ALARM_ENABLED, model.isEnabled)

            var repeatingDays = ""
            for (i in 0..6) {
                repeatingDays += model.getRepeatingDay(i).toString() + ","
            }
            repeatingDays = repeatingDays.dropLast(1)
            values.put(KEY_ALARM_REPEATE_DAYS, repeatingDays)

            return values
        }

        /**
         * Đổi ContentValue to MyAlarm
         */
        private fun populateModel(model: MyAlarm, c: Cursor){
            BaseDataHandle.populateModel(model, c)

            model.name = c.getString(c.getColumnIndex(KEY_ALARM_NAME))
            model.timeHour = c.getInt(c.getColumnIndex(KEY_ALARM_HOUR))
            model.timeMinute = c.getInt(c.getColumnIndex(KEY_ALARM_MINUTE))
            model.alarmTone = Uri.parse(c.getString(c.getColumnIndex(KEY_ALARM_TONE)))
            model.isVibration = c.getInt(c.getColumnIndex(KEY_ALARM_VIBRATION)) !== 0
            model.isEnabled = c.getInt(c.getColumnIndex(KEY_ALARM_ENABLED)) !== 0

            val repeatingDays = c.getString(c.getColumnIndex(KEY_ALARM_REPEATE_DAYS)).split(",")
            for (i in repeatingDays.indices) {
                model.setRepeatingDay(i, repeatingDays[i] != "false")
            }
        }

        fun getById(context: Context, id: Long): MyAlarm {
            var item = MyAlarm()
            var db: SQLiteDatabase? = null
            val where = "$KEY_ID=$id"
            try {
                db  = BaseDataHandle(context).writableDatabase
                val cursor = db.query(TABLE_ALARM, null, where, null, null, null, null)
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

        fun getAll(context: Context): ArrayList<MyAlarm> {
            var listAlarm = ArrayList<MyAlarm>()

            var db: SQLiteDatabase? = null
            val query = "SELECT * FROM $TABLE_ALARM";
            try {
                db  = BaseDataHandle(context).writableDatabase
                val cursor = db.rawQuery(query, null)
                while (cursor.moveToNext()) {
                    var item = MyAlarm()
                    populateModel(item, cursor)
                    listAlarm.add(item)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                close(db)
            }
            return listAlarm
        }

        fun add(context: Context, model: MyAlarm) : Long {
            val values = populateContent(model)
            return BaseDataHandle.insert(context, TABLE_ALARM, values)
        }

        fun update(context: Context, model: MyAlarm): Int {
            var values = populateContent(model)
            var where = "$KEY_ID = ${model.id}"
            return BaseDataHandle.update(context, TABLE_ALARM, values, where)
        }

        fun delete(context: Context, alarmId: Long) : Int {
            var where = "$KEY_ID = $alarmId"
            return BaseDataHandle.delete(context, TABLE_ALARM, where)
        }

        fun setEnable(context: Context, alarmId: Long, enable: Boolean): Int {
            val values = ContentValues()
            values.put(KEY_ALARM_ENABLED, enable)

            var whereStr = "$KEY_ID = $alarmId"

            return BaseDataHandle.update(context, TABLE_ALARM, values, whereStr)

        }

    }
}