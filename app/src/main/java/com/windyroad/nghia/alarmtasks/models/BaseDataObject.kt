package com.windyroad.nghia.alarmtasks.models

import com.windyroad.nghia.common.network.UploadStatus
import java.util.*

/**
 * Created by Nghia on 2/18/2018.
 */
/**
 * Created by Nghia-PC on 8/19/2015.
 * Lớp mẫu cho Data - Chứa thuộc tính chung
 */
open class BaseDataObject {
    var id: Long = -1
    var serverId: Long = -1
    var sessionCode: String? = null
    var createBy: Long = -1
    var createAt: Calendar? = null
    var uploadStatus: UploadStatus? = null
    var isSelected = false // Selected on ListView, not relate database

    constructor() {
        createAt = Calendar.getInstance()
    }

    constructor(_id: Long, serverId: Long, sessionCode: String, createBy: Long, createAt: Calendar, uploadStatus: UploadStatus) {
        this.id = _id
        this.serverId = serverId
        this.sessionCode = sessionCode
        this.createBy = createBy
        this.createAt = createAt
        this.uploadStatus = uploadStatus
    }
}