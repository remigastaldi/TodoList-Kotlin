package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import com.example.gastalr.todolist.Helper.SwipeAndDragHelper
import com.example.gastalr.todolist.extensions.launchActivity
import com.example.gastalr.todolist.sql.TaskContract
import com.example.gastalr.todolist.sql.TaskDbHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MyAdapter(private val context: Context, private val mHelper : TaskDbHelper) :  RecyclerView.Adapter<MyViewHolder>(),  SwipeAndDragHelper.ActionCompletionContract {

    private var touchHelper: ItemTouchHelper? = null
    private var list = mutableListOf<MyObject>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        initList()
        super.onAttachedToRecyclerView(recyclerView)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_cards, viewGroup, false)
        return MyViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val myObject = list[position]
        myViewHolder.bind(myObject)
        val activity: Activity = context as Activity

        myViewHolder.textView.setOnClickListener{

            activity.launchActivity<AddNoteActivity>(42) {
                putExtra("PositionInList", position.toString())
                putExtra("TaskTitle", myObject.title)
                putExtra("TaskText", myObject.text)
            }
        }

        activity.recyclerView.setOnTouchListener({ _, event ->
            when (event.actionMasked)
            {
                MotionEvent.ACTION_UP -> {
                    updateNotesPositionInDb()
                    false
                }

                else -> {
                    false
                }
            }
        })

        myViewHolder.titleView.setOnTouchListener({ _, event ->
            when (event.actionMasked)
            {
                MotionEvent.ACTION_DOWN -> {
                    touchHelper!!.startDrag(myViewHolder)
                    false
                }
                else -> {
                    false
                }
            }

/*            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                press = true
                touchHelper!!.startDrag(myViewHolder)
             } */
        })
    }

    private fun initList() {
        list.clear()
        val db = mHelper.readableDatabase
        val cursor = db.query(TaskContract.TaskEntry.TABLE,
                arrayOf(TaskContract.TaskEntry.ID,
                        TaskContract.TaskEntry.COL_TASK_TITLE,
                        TaskContract.TaskEntry.COL_TASK_TEXT,
                        TaskContract.TaskEntry.COL_TASK_DATE),null, null, null, null, TaskContract.TaskEntry.ID)
        while (cursor.moveToNext()) {
            val id = cursor.getColumnIndex(TaskContract.TaskEntry.ID)
            val idTitle = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
            val idText = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TEXT)
            val idDate = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE)

            list.add(MyObject(cursor.getString(id), cursor.getString(idTitle), cursor.getString(idText), cursor.getString(idDate)))
        }
        notifyDataSetChanged()

        cursor.close()
        db.close()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        val target = list[oldPosition]
        list.removeAt(oldPosition)
        list.add(newPosition, target)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun onViewSwiped(position: Int) {
        deleteTask(list[position].ID)
        list.removeAt(position)
        notifyItemRemoved(position)
        updateNotesPositionInDb()
    }

    fun setTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }

    fun addTask(taskTitle : String, taskText: String) {
        val values = ContentValues()

        val sdf = SimpleDateFormat("dd/MM/yyyy/", Locale.US)
        val date = sdf.format(Date())

        values.put(TaskContract.TaskEntry.ID, list.size)
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskTitle)
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, taskText)
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, date)

        val db = mHelper.readableDatabase
        val id = db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE)
        db.close()

        list.add(MyObject(list.size.toString(), taskTitle, taskText, date))

        notifyItemInserted(list.size)
    }

    fun addTask() {
        val test: Activity = context as Activity

        test.launchActivity<AddNoteActivity>(42) {
           /* putExtra("user", "854")
            p utExtra("user2", "46850") */
        }
    }

    fun deleteTask(taskId: String) {

        val db = mHelper.readableDatabase
        db.delete(TaskContract.TaskEntry.TABLE,
                "id=" + taskId, null)
        db.close()
    }

    fun modifyTask(taskPosition: String, taskTitle: String, taskText: String) {
        val target = list[taskPosition.toInt()]

        target.title = taskTitle
        target.text = taskText

        val values = ContentValues()

        val sdf = SimpleDateFormat("dd/MM/yyyy/", Locale.US)
        val date = sdf.format(Date())

        values.put(TaskContract.TaskEntry.ID, taskPosition)
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskTitle)
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, taskText)
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, date)

        val db = mHelper.readableDatabase
        db.update(TaskContract.TaskEntry.TABLE,
                values, TaskContract.TaskEntry.ID + "=" + target.ID, null)

        db.close()

        notifyItemChanged(taskPosition.toInt())
    }

    fun updateNotesPositionInDb() {
        val db = mHelper.readableDatabase

        var i = 0
        while (i < list.size) {
            val values = ContentValues()

            values.put(TaskContract.TaskEntry.ID, i)
            db.update(TaskContract.TaskEntry.TABLE,
                    values, TaskContract.TaskEntry.ID + "=? AND " + TaskContract.TaskEntry.COL_TASK_TITLE + "=?", arrayOf(list[i].ID, list[i].title))
            i++

        }
        db.close()
    }
}