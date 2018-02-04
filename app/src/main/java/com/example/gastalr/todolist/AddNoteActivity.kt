package com.example.gastalr.todolist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

/**
 * Created by gastal_r on 2/4/18.
 */

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra("user")
        val userId2 = intent.getStringExtra("user2")
        requireNotNull(userId) { "no user_id provided in Intent extras" }

        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, userId2, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val data = Intent()

        data.putExtra("Result", "Boinsoir")
        setResult(Activity.RESULT_OK, data)

        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {

        Toast.makeText(this, "EXIT", Toast.LENGTH_SHORT).show()
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

            R.id.action_validate ->


                return true

            android.R.id.home ->
                onBackPressed()
        }

        return super.onOptionsItemSelected(item)

    }
}