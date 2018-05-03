package com.example.myeongsic.expandablerecyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_header.view.*


import java.util.ArrayList

/**
 * Created by anandbose on 09/06/15.
 */
data class Item (var type: Int? = null, var text: String? = null, var invisibleChildren: ArrayList<Item>? = null)
class ExpandableListAdapter(private val data: MutableList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val HEADER = 0
    val CHILD = 1

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var view: View? = null
        val context = parent.context
        val dp = context.resources.displayMetrics.density
        val subItemPaddingLeft = (18 * dp).toInt()
        val subItemPaddingTopAndBottom = (5 * dp).toInt()
        when (type) {
            HEADER -> {
                val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.list_header, parent, false)
                return ListHeaderViewHolder(view)
            }
            CHILD -> {
                val itemTextView = TextView(context)
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom)
                itemTextView.setTextColor(-0x78000000)
                itemTextView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                return object : RecyclerView.ViewHolder(itemTextView) {

                }
            }
        }
        return ListHeaderViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (item.type) {
            HEADER -> {
                val itemController = holder as ListHeaderViewHolder
                itemController.refferalItem = item
                itemController.header_title.text = item.text
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus)
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus)
                }
                itemController.btn_expand_toggle.setOnClickListener {
                    if (item.invisibleChildren == null) {
                        item.invisibleChildren = ArrayList()
                        var count = 0
                        val pos = data.indexOf(itemController.refferalItem!!)
                        while (data.size > pos + 1 && data[pos + 1].type == CHILD) {
                            item.invisibleChildren!!.add(data.removeAt(pos + 1))
                            count++
                        }
                        notifyItemRangeRemoved(pos + 1, count)
                        itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus)
                    } else {
                        val pos = data.indexOf(itemController.refferalItem!!)
                        var index = pos + 1
                        for (i in item.invisibleChildren!!) {
                            data.add(index, i)
                            index++
                        }
                        notifyItemRangeInserted(pos + 1, index - pos - 1)
                        itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus)
                        item.invisibleChildren = null
                    }
                }
            }
            CHILD -> {
                val itemTextView = holder.itemView as TextView
                itemTextView.text = data[position].text
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type!!
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private class ListHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var header_title: TextView = itemView.header_title
        var btn_expand_toggle: ImageView = itemView.btn_expand_toggle
        var refferalItem: Item? = null
    }




}
