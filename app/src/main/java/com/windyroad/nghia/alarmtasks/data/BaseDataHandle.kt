package com.windyroad.nghia.alarmtasks.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.windyroad.nghia.alarmtasks.models.BaseDataObject
import com.windyroad.nghia.common.ConvertUtil
import com.windyroad.nghia.common.network.UploadStatus
import com.windyroad.nghia.common.sqlite.DatabaseUtil
import java.util.*

/**
 * Created by Nghia on 2/17/2018.
 */
open class BaseDataHandle(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME , null, DATABASE_VERSION) {

     val LOG = BaseDataHandle::class.java!!.simpleName

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "alarm_tasks"

        //---- Column Chung----
        val KEY_ID = "_id"
        val KEY_SERVER_ID = "server_id"
        val KEY_SESSION_CODE = "session_code"
        val KEY_CREATE_BY = "create_by"
        val KEY_CREATE_AT = "create_at"
        val KEY_UPLOAD_STATUS = "upload_status"
        val CREATE_COMMON_COLUMNS = KEY_ID + " INTEGER  PRIMARY KEY  AUTOINCREMENT, " +
                "$KEY_SERVER_ID INTEGER DEFAULT -1,  " +
                "$KEY_SESSION_CODE TEXT DEFAULT 0,  " +
                "$KEY_CREATE_BY INTEGER DEFAULT 0,  " +
                "$KEY_CREATE_AT DATETIME DEFAULT CURRENT_TIMESTAMP,  " +
                "$KEY_UPLOAD_STATUS TEXT DEFAULT '${UploadStatus.WAITING_UPLOAD}'"


        /** Chuyển BaseDataObject => Content Values; Không Put Id  */
        fun populateContent(item: BaseDataObject) : ContentValues {
            var values = ContentValues()

            val createAt = ConvertUtil.Calendar2String(item.createAt, null, null)

            //values.put(KEY_ID, item.id);
            values.put(KEY_SERVER_ID, item.serverId)
            values.put(KEY_SESSION_CODE, item.sessionCode)
            values.put(KEY_CREATE_BY, item.createBy)
            values.put(KEY_CREATE_AT, createAt)
            values.put(KEY_UPLOAD_STATUS, item.uploadStatus?.name)

            return values;
        }

        /** Chuyển Cursor => BaseDataObject  */
        fun populateModel(item: BaseDataObject, cursor: Cursor){

            // Lấy, Truyền dữ liệu
            item.id = cursor.getLong(cursor.getColumnIndex(KEY_ID))
            item.serverId = cursor.getLong(cursor.getColumnIndex(KEY_SERVER_ID))
            item.sessionCode = cursor.getString(cursor.getColumnIndex(KEY_SESSION_CODE))
            item.createBy = cursor.getLong(cursor.getColumnIndex(KEY_CREATE_BY))
            item.createAt = ConvertUtil.String2Calendar(
                    cursor.getString(cursor.getColumnIndex(KEY_CREATE_AT)), null, null, Calendar.getInstance())

            var uploadStatus = cursor.getString(cursor.getColumnIndex(KEY_UPLOAD_STATUS))
            if (uploadStatus == null) { item.uploadStatus = UploadStatus.WAITING_UPLOAD}
            else item.uploadStatus = UploadStatus.valueOf(uploadStatus)
        }

        /** Reset Database: Xóa hết rồi tạo lại. True: thành công  */
        fun resetAllTable(context: Context): Boolean {
            var isSuccess = false
            var db: SQLiteDatabase? = null
            try {
                val dataHelper = BaseDataHandle(context)
                db = dataHelper.writableDatabase
                dataHelper.onUpgrade(db, 0, 0)
                isSuccess = true

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                close(db)
            }
            return isSuccess
        }

        /** Đóng Database  */
        fun close(db: SQLiteDatabase?) {
            if (db != null)
                if (db.isOpen)
                    db.close()
        }

        /** Tạo mới
         *
         * @param context
         * @param tableName
         * @param values
         * @return new id, -1 nếu fail
         */
        internal fun insert(context: Context, tableName: String, values: ContentValues): Long {
            var resultId: Long = DatabaseUtil.QUERY_FAIL.toLong()
            var db: SQLiteDatabase? = null
            try {
                db = BaseDataHandle(context).writableDatabase

                resultId = db!!.insert(tableName, null, values)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                BaseDataHandle.close(db)
            }
            return resultId
        }

        /** Cập nhật
         *
         * @param context
         * @param tableName
         * @param values
         * @param strWhere
         * @return dòng ảnh hưởng
         */
        internal fun update(context: Context, tableName: String, values: ContentValues, strWhere: String): Int {
            var resultRowAffect: Int = DatabaseUtil.QUERY_FAIL
            var db: SQLiteDatabase? = null
            try {
                db = BaseDataHandle(context).writableDatabase

                resultRowAffect = db!!.update(tableName, values, strWhere, null)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                BaseDataHandle.close(db)
            }
            return resultRowAffect
        }

        /** Xóa
         *
         * @param context
         * @param tableName
         * @param strWhere
         * @return dòng ảnh hưởng
         */
        internal fun delete(context: Context, tableName: String, strWhere: String?): Int {
            var resultRowAffect: Int = DatabaseUtil.QUERY_FAIL
            var db: SQLiteDatabase? = null
            try {
                db = BaseDataHandle(context).writableDatabase

                resultRowAffect = db!!.delete(tableName, strWhere, null)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                BaseDataHandle.close(db)
            }
            return resultRowAffect
        }

        /** Đếm số dòng  */
        internal fun count(context: Context, tableName: String, strWhere: String): Int {
            var resultRow = DatabaseUtil.QUERY_FAIL
            var db: SQLiteDatabase? = null
            val query = "SELECT COUNT(*) as count " +
                    " FROM " + tableName +
                    " WHERE " + strWhere
            try {
                db = BaseDataHandle(context).writableDatabase

                val cursor = db!!.rawQuery(query, null)
                cursor.moveToFirst()
                resultRow = cursor.getInt(0)

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                BaseDataHandle.close(db)
            }
            return resultRow
        }

        /** Cập nhật lại khi gởi xong server  */
        internal fun update_UploadStatus_ServerId_byId(
                context: Context, tableName: String, id: Long, uploadStatus: UploadStatus, serverId: Long): Int {

            val values = ContentValues()
            values.put(KEY_UPLOAD_STATUS, uploadStatus.name)
            values.put(KEY_SERVER_ID, serverId)

            val strWhere = "$KEY_ID=$id"

            return BaseDataHandle.update(context, tableName, values, strWhere)
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyAlarmData.MY_ALARM_CREATE_STATEMENT)
        db?.execSQL(HistoryData.HISTORY_CREATE_STATEMENT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // XÓA
        db?.execSQL("DROP TABLE IF EXISTS " + MyAlarmData.TABLE_ALARM)
        // TẠO LẠI
        onCreate(db)
    }
}
