package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.example.gastalr.todolist.Helper.SwipeAndDragHelper
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.support.v7.widget.helper.ItemTouchHelper



class MyAdapter(private var list: MutableList<MyObject>) :  RecyclerView.Adapter<MyViewHolder>(),  SwipeAndDragHelper.ActionCompletionContract {

    private var touchHelper: ItemTouchHelper? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        println("Create\n")
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_cards, viewGroup, false)
        return MyViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val myObject = list[position]
        println("Bind")
        myViewHolder.bind(myObject)
        myViewHolder.imageView.setOnTouchListener(OnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                println("Touch")
                touchHelper!!.startDrag(myViewHolder)
            }
            false
        })
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
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }
}