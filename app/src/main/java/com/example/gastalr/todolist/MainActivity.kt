package com.example.gastalr.todolist

/**
 * Created by gastal_r on 2/3/18.
 */

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.collections.*
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.gastalr.todolist.Helper.SwipeAndDragHelper




class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null

    private val cities: MutableList<MyObject> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //remplir la ville

        recyclerView = this.findViewById(R.id.recyclerView)

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview

        val adapter = MyAdapter(cities)


        val swipeAndDragHelper = SwipeAndDragHelper(adapter)
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)

        adapter.setTouchHelper(touchHelper)

        recyclerView!!.adapter = adapter

        touchHelper.attachToRecyclerView(recyclerView);
        ajouterVilles()
    }

    private fun ajouterVilles() {
        cities.add(MyObject("France", "http://www.telegraph.co.uk/travel/destination/article130148.ece/ALTERNATES/w620/parisguidetower.jpg"))
        cities.add(MyObject("Angleterre", "http://www.traditours.com/images/Photos%20Angleterre/ForumLondonBridge.jpg"))
        cities.add(MyObject("Allemagne", "http://tanned-allemagne.com/wp-content/uploads/2012/10/pano_rathaus_1280.jpg"))
        cities.add(MyObject("Espagne", "http://www.sejour-linguistique-lec.fr/wp-content/uploads/espagne-02.jpg"))
        cities.add(MyObject("Italie", "http://retouralinnocence.com/wp-content/uploads/2013/05/Hotel-en-Italie-pour-les-Vacances2.jpg"))
        cities.add(MyObject("Russie", "http://www.choisir-ma-destination.com/uploads/_large_russie-moscou2.jpg"))
    }
}
