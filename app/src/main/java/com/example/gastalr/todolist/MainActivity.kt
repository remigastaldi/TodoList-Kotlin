package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */

import android.app.Activity
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
import android.content.Intent
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var mHelper: TaskDbHelper = TaskDbHelper(this)
    private val adapter = MyAdapter(mHelper)
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = this.findViewById(R.id.recyclerView)

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview

      //  this.deleteDatabase("com.gastalr.todolist.db");



        val swipeAndDragHelper = SwipeAndDragHelper(adapter)
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)

        adapter.setTouchHelper(touchHelper)

        recyclerView!!.adapter = adapter

        touchHelper.attachToRecyclerView(recyclerView)

        //adapter.setList(cities)
        val db = mHelper.writableDatabase
      //  db.execSQL("DROP TABLE "+TaskContract.TaskEntry.TABLE)
        //db.delete(TaskContract.TaskEntry.TABLE, null, null)
        val floatingButton: FloatingActionButton = this.findViewById(R.id.floating_button)

        floatingButton.setOnClickListener {
            adapter.addTaskButton(this)
        }

        val cursor = db.query(TaskContract.TaskEntry.TABLE,
                arrayOf(TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
            Log.d(TAG, "Task: " + cursor.getString(idx))
        }
        cursor.close()
        db.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 42) {
            if (resultCode == Activity.RESULT_OK) {
                val action = data.getStringExtra("Action")
                println("========= " + action)

                when (action)
                {
                    "ADD" -> {
                        val taskTitle = data.getStringExtra("TaskTitle")
                        val taskText = data.getStringExtra("TaskText")
                        println("========= " + taskTitle)
                        println("========= " + taskText)
                        adapter.addTask(taskTitle, taskText)
                    }
                }
                //Toast.makeText(this, data.getStringExtra("Result"), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
