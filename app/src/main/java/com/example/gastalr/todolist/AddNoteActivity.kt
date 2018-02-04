package com.example.gastalr.todolist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.EditText



/**
 * Created by gastal_r on 2/4/18.
 */

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

     /*   val userId = intent.getStringExtra("user")
        val userId2 = intent.getStringExtra("user2")
        requireNotNull(userId) { "no user_id provided in Intent extras" }

        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, userId2, Toast.LENGTH_SHORT).show()
*/
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

                val data = Intent()

                data.putExtra("Action", "ADD")

                data.putExtra("TaskTitle", editTextTitle.text.toString())
                data.putExtra("TaskText", editTextText.text.toString())

                setResult(Activity.RESULT_OK, data)

                finish()
            }

            android.R.id.home ->
                onBackPressed()
        }

        return super.onOptionsItemSelected(item)

    }
}