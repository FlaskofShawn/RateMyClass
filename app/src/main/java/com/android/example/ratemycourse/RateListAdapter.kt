package com.android.example.ratemycourse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class RateListAdapter(private var rates: MutableList<String>) : RecyclerView.Adapter<RateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rate_list_item, parent, false)
        return RateViewHolder(view)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val item = rates[position]
        holder.apply {
            // TODO: PHASE 3.1 - Re-define these values based on the the post object being displayed
//            user_email.text = rates[position].userEmail
//            rate_text.text = rates[position].score.toString()
            comment_text.text = rates[position]
        }
    }

    override fun getItemCount() = rates.size

    fun setData(newRates: MutableList<String>) {
        rates = newRates
        this.notifyDataSetChanged()
    }
}

class RateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val rateItem: ConstraintLayout = view.findViewById(R.id.rateItem)
//    val user_email: TextView = view.findViewById(R.id.user_email)
    val comment_text: TextView = view.findViewById(R.id.comment_text)
//    val rate_text: TextView = view.findViewById(R.id.rate_text)

}