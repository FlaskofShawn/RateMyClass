package com.android.example.ratemycourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClassSelectionFragment: Fragment() {

    var courses: MutableList<Course> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classFeedRecyclerView = view.findViewById<RecyclerView>(R.id.select_course_recycle_view)
        classFeedRecyclerView.layoutManager = LinearLayoutManager(view.context)
        val classFeedAdapter = ClassSelectionAdapter(courses)
        classFeedRecyclerView.adapter = classFeedAdapter

        FirebaseAccessor.setupAllCourses { exception, courses ->
            if (exception == null) {
                this.courses = courses
                // You can update recycler view data source here.
                classFeedAdapter.setData(this.courses)
            } else {
                Toast.makeText(this.context, "Failed to fetch courses", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.confirm_add_button).setOnClickListener{
            if (classFeedAdapter.selectedCourse == -1) {
                Toast.makeText(this.context, "You haven't select any course.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                User.curUser.addCourse (courses[classFeedAdapter.selectedCourse].id!!) {
                    if (it != null) {
                        Toast.makeText(this.context, "Failed to add course", Toast.LENGTH_SHORT).show()
                    } else {
                        findNavController().navigate(ClassSelectionFragmentDirections.actionClassSelectionFragmentToClassFeedFragment())
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener{
            val action = ClassSelectionFragmentDirections.actionClassSelectionFragmentToClassFeedFragment()
            it.findNavController().navigate(action)
        }

    }
}