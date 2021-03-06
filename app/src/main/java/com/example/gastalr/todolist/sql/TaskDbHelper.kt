package com.example.gastalr.todolist.sql

/**
 * Created by gastal_r on 2/4/18.
 */
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class TaskDbHelper(context: Context) : SQLiteOpenHelper(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " (" +
                TaskContract.TaskEntry.ID + " INTEGER, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_TEXT + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL);"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE)
        onCreate(db)
    }
}