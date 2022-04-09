package com.android.example.ratemycourse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_class_display.*
import kotlinx.android.synthetic.main.fragment_class_feed.*


class ClassDisplayFragment : Fragment() {

    private val args: ClassDisplayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_display, container, false)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var course = args.classObject
        val rateRecyclerView = view.findViewById<RecyclerView>(R.id.rate_recycler_view)
        val rateListAdapter = RateListAdapter(course.comments)
        rateRecyclerView.adapter = rateListAdapter
        rateRecyclerView.layoutManager = LinearLayoutManager(context)

        val classNameLabel = view.findViewById<TextView>(R.id.class_name)
        classNameLabel.text = course.name

        val classRateLabel = view.findViewById<TextView>(R.id.class_rate)
        classRateLabel.text = String.format("%.1f", course.score)

        val classDesLabel = view.findViewById<TextView>(R.id.description)
        classDesLabel.text = course.description

        rate_button.setOnClickListener{
            val action = ClassDisplayFragmentDirections.actionClassDisplayFragmentToRateFragment(course)
            findNavController().navigate(action)
        }
    }
}