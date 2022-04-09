package com.android.example.ratemycourse

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
//import androidx.navigation.fragment
import androidx.recyclerview.widget.RecyclerView
import java.math.BigDecimal


class ClassSelectionAdapter(private var posts: MutableList<Course>) : RecyclerView.Adapter<SelectionItemViewHolder>() {
    var selectedCourse = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.class_list_item, parent, false)
        return SelectionItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectionItemViewHolder, position: Int) {
        val item = posts[position]
        holder.apply {
            className.text = item.name.toString()
            classRate.text = String.format("%.1f", item.score)

            if (selectedCourse != -1) {
                if (selectedCourse != position) {
                    holder.itemView.setBackgroundResource(R.drawable.round_corner_bg)
                }
            }
        }

        holder.classItem.setOnClickListener{
            holder.itemView.setBackgroundResource(R.drawable.round_corner_selected_bg)
            this.selectedCourse = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = posts.size

    fun setData(newPosts: MutableList<Course>) {
        posts = newPosts
        this.notifyDataSetChanged()
    }
}

class SelectionItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var classItem = view.findViewById<ConstraintLayout>(R.id.class_item)
    val className  = view.findViewById<TextView>(R.id.class_name)
    val classRate  = view.findViewById<TextView>(R.id.class_rate)
}