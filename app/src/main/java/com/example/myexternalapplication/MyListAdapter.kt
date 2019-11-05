package com.example.myexternalapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyListAdapter(private val humanList: List<Human>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View =
            LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.list_item_view, parent, false)

        view.findViewById<TextView>(R.id.guest_name_textview).text = humanList[position].name

        view.findViewById<TextView>(R.id.human_type_textview).text =
            when ((humanList[position].type % 2) > 0) {
                true -> "Type A"
                else -> "Type B"
            }

        return view
    }

    override fun getItem(position: Int): Human {
        return humanList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return humanList.size
    }
}