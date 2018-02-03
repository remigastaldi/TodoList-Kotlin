package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import com.squareup.picasso.Picasso


class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textViewView: TextView = itemView.findViewById<View>(R.id.text) as TextView
    val imageView: ImageView = itemView.findViewById<View>(R.id.image) as ImageView


    fun bind(myObject: MyObject) {
        textViewView.text = myObject.text
        Picasso.with(imageView.context).load(myObject.imageUrl).centerCrop().fit().into(imageView)
    }

}