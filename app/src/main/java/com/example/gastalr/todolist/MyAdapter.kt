package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import com.example.gastalr.todolist.Helper.SwipeAndDragHelper
import com.example.gastalr.todolist.extensions.launchActivity
import com.example.gastalr.todolist.sql.TaskContract
import com.example.gastalr.todolist.sql.TaskDbHelper


class MyAdapter(private val mHelper : TaskDbHelper) :  RecyclerView.Adapter<MyViewHolder>(),  SwipeAndDragHelper.ActionCompletionContract {

    private var touchHelper: ItemTouchHelper? = null
    private var list = mutableListOf<MyObject>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        println("Attached")
        initList()
        super.onAttachedToRecyclerView(recyclerView)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        println("Create")
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_cards, viewGroup, false)
        return MyViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val myObject = list[position]
        println("Bind")
        myViewHolder.bind(myObject)
        myViewHolder.titleView.setOnTouchListener({ _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                println("Touch")
                touchHelper!!.startDrag(myViewHolder)
            }
            false
        })
    }

    private fun initList() {
        list.clear()
        val db = mHelper.readableDatabase
        val cursor = db.query(TaskContract.TaskEntry.TABLE,
                arrayOf(TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_TEXT), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getColumnIndex(TaskContract.TaskEntry._ID)
            val idTitle = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
            val idText = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TEXT)
            println("ADD TO LIST " + cursor.getString(idTitle) + " " + cursor.getString(idText))
            list.add(MyObject(cursor.getString(id), cursor.getString(idTitle), cursor.getString(idText)))
        }
        notifyDataSetChanged()

        cursor.close()
        db.close()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        val targetUser = list[oldPosition]
        list.removeAt(oldPosition)
        list.add(newPosition, targetUser)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun onViewSwiped(position: Int) {
        deleteTask(list[position]._ID)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }

    fun addTask(taskTitle : String, taskText: String) {
        val values = ContentValues()

        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskTitle)
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, taskText)

        val db = mHelper.readableDatabase
        val id = db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE)
        db.close()

        list.add(MyObject(id.toString(), taskTitle, taskText))
        notifyItemInserted(list.size)
    }

    fun addTaskButton(context: Context) {
        val test: Activity = context as Activity

        test.launchActivity<AddNoteActivity>(42) {
            putExtra("user", "854")
            putExtra("user2", "46850")
        }
        /*
        val title = "test"
        val task = "Oui bonsoir famille"
        val db = mHelper.readableDatabase
        val values = ContentValues()

        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, title)
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, task)

        val id = db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE)
        db.close()

        list.add(MyObject(id.toString(), title, task))
        notifyItemInserted(list.size)
        */
    }

    fun deleteTask(taskId: String) {

        val db = mHelper.readableDatabase
        db.delete(TaskContract.TaskEntry.TABLE,
                "id=" + taskId, null)
        db.close()
    }

}