package com.example.myeongsic.expandablerecyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.myeongsic.expandablerecyclerview.Item
import com.example.myeongsic.expandablerecyclerview.R

import kotlinx.android.synthetic.main.activity_main.*


import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    val HEADER = 0
    val CHILD = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val data = ArrayList<Item>()

        data.add(Item(HEADER, "Fruits"))
        data.add(Item(CHILD, "Apple"))
        data.add(Item(CHILD, "Orange"))
        data.add(Item(CHILD, "Banana"))
        data.add(Item(HEADER, "Cars"))
        data.add(Item(CHILD, "Audi"))
        data.add(Item(CHILD, "Aston Martin"))
        data.add(Item(CHILD, "BMW"))
        data.add(Item(CHILD, "Cadillac"))

        val places = Item(HEADER, "Places")
        places.invisibleChildren = ArrayList()
        places.invisibleChildren!!.add(Item(CHILD, "Kerala"))
        places.invisibleChildren!!.add(Item(CHILD, "Tamil Nadu"))
        places.invisibleChildren!!.add(Item(CHILD, "Karnataka"))
        places.invisibleChildren!!.add(Item(CHILD, "Maharashtra"))

        data.add(places)

        recyclerview!!.adapter = ExpandableListAdapter(data)
    }
}
