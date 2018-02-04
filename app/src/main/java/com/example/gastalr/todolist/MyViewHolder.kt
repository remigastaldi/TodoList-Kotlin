package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */


import android.view.View
import android.widget.TextView
import android.support.v7.widget.RecyclerView


class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView: TextView = itemView.findViewById<View>(R.id.title) as TextView
    val textView: TextView = itemView.findViewById<View>(R.id.text) as TextView
    val dateTextView: TextView = itemView.findViewById<View>(R.id.date) as TextView

    fun bind(myObject: MyObject) {
        titleView.text = myObject.title
        textView.text = myObject.text
        dateTextView.text = myObject.date
    }

}