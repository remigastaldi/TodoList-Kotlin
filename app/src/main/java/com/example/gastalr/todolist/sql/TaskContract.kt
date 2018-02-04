package com.example.gastalr.todolist.sql

/**
 * Created by gastal_r on 2/4/18.
 */

import android.provider.BaseColumns

class TaskContract {
    companion object {
        val DB_NAME: String = "com.gastalr.todolist.db"
        val DB_VERSION: Int = 1
    }

    class TaskEntry: BaseColumns {
        companion object {
            val TABLE: String = "tasks"
            val COL_TASK_TITLE: String = "title"
            val COL_TASK_TEXT: String = "text"
            val COL_TASK_DATE: String = "date"

            var _ID: String = "id"
        }
    }
}