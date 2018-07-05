package com.windyroad.nghia.alarmtasks.models

class HistoryExport {
    var taskName: String = ""
    var createTime: String = ""
    var createDate: String = ""

    constructor()

    constructor(taskName: String, createTime: String, createDate: String) {
        this.taskName = taskName
        this.createTime = createTime
        this.createDate = createDate
    }
}