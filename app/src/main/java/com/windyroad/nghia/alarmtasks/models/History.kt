package com.windyroad.nghia.alarmtasks.models

import android.net.Uri

/**
 * Created by ADMIN on 3/18/2018.
 */
class History : BaseDataObject {

    var taskName: String = ""

    constructor() {

    }

    constructor(taskName : String){
        this.taskName = taskName
    }

}