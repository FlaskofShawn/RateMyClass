package com.android.example.ratemycourse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
//import androidx.navigation.fragment
import androidx.recyclerview.widget.RecyclerView

class MainClassListAdapter(private var posts: MutableList<Course>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.class_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = posts[position]
        holder.apply {
            className.text = item.name.toString()
            classRate.text = String.format("%.1f", item.score)
        }

        holder.classItem.setOnClickListener{
            val action = ClassFeedFragmentDirections.actionClassFeedFragmentToClassDisplayFragment(posts[position])
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = posts.size

    fun setData(newPosts: MutableList<Course>) {
        posts = newPosts
        this.notifyDataSetChanged()
    }
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var classItem = view.findViewById<ConstraintLayout>(R.id.class_item)
    val className  = view.findViewById<TextView>(R.id.class_name)
    val classRate  = view.findViewById<TextView>(R.id.class_rate)
}