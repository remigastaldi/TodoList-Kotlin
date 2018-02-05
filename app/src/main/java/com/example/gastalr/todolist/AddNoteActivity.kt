package com.example.gastalr.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast


/**
 * Created by gastal_r on 2/4/18.
 */

class AddNoteActivity : AppCompatActivity() {

        var position: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        position = intent.getStringExtra("PositionInList")

        val title = intent.getStringExtra("TaskTitle")
        val text = intent.getStringExtra("TaskText")

        val editTextTitle = findViewById<EditText>(R.id.title_edit_text)
        val editTextText = findViewById<EditText>(R.id.task_edit_text)

        editTextTitle.setText(title)
        editTextText.setText(text)
    }

    override fun onBackPressed() {
        val data = Intent()

        data.putExtra("Action", "CANCEL")
        setResult(Activity.RESULT_OK, data)

        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_validate -> {
                val editTextTitle = findViewById<EditText>(R.id.title_edit_text)
                val editTextText = findViewById<EditText>(R.id.task_edit_text)

                var taskTitle = editTextTitle.text.toString()
                val taskText = editTextText.text.toString()


                if (taskTitle.isEmpty() && taskText.isEmpty())
                {
                    Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show()
                    return super.onOptionsItemSelected(item)
                }
                else if (taskTitle.isEmpty())
                    taskTitle = taskText

                val data = Intent()

                if (position != null)
                {
                    data.putExtra("Action", "MODIFY")
                    data.putExtra("PositionInList", position)

                }
                else
                    data.putExtra("Action", "ADD")


                data.putExtra("TaskTitle", taskTitle)
                data.putExtra("TaskText", taskText)

                setResult(Activity.RESULT_OK, data)

                finish()
            }

            android.R.id.home ->
                onBackPressed()
        }

        return super.onOptionsItemSelected(item)

    }
}