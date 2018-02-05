package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.example.gastalr.todolist.Helper.SwipeAndDragHelper
import com.example.gastalr.todolist.sql.TaskContract
import com.example.gastalr.todolist.sql.TaskDbHelper


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var mHelper: TaskDbHelper = TaskDbHelper(this)
    private val adapter = MyAdapter(this, mHelper)
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = this.findViewById(R.id.recyclerView)

        recyclerView!!.layoutManager = LinearLayoutManager(this)

        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));


      //  this.deleteDatabase("com.gastalr.todolist.db");



        val swipeAndDragHelper = SwipeAndDragHelper(adapter)
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)

        adapter.setTouchHelper(touchHelper)

        recyclerView!!.adapter = adapter

        touchHelper.attachToRecyclerView(recyclerView)

        val db = mHelper.writableDatabase
      //  db.execSQL("DROP TABLE "+TaskContract.TaskEntry.TABLE)
        //db.delete(TaskContract.TaskEntry.TABLE, null, null)
        val floatingButton: FloatingActionButton = this.findViewById(R.id.floating_button)

        floatingButton.setOnClickListener {
            adapter.addTask()
        }

        val cursor = db.query(TaskContract.TaskEntry.TABLE,
                arrayOf(TaskContract.TaskEntry.ID, TaskContract.TaskEntry.COL_TASK_TITLE), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
            Log.d(TAG, "Task: " + cursor.getString(idx))
        }
        cursor.close()
        db.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode)
        {
            42 -> {
                val action = data.getStringExtra("Action")

                when (action) {
                    "ADD" -> {
                        val taskTitle = data.getStringExtra("TaskTitle")
                        val taskText = data.getStringExtra("TaskText")

                        adapter.addTask(taskTitle, taskText)
                    }

                    "MODIFY" -> {
                        val taskPosition = data.getStringExtra("PositionInList")
                        val taskTitle = data.getStringExtra("TaskTitle")
                        val taskText = data.getStringExtra("TaskText")

                        adapter.modifyTask(taskPosition, taskTitle, taskText)
                    }
                }
            }
        }
    }
}
