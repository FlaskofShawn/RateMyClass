package com.android.example.ratemycourse

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase

class ClassFeedFragment : Fragment() {
    var postlist = mutableListOf<Course>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // TODO: PHASE 3.1 - Connect adapter and layoutManager to the RecyclerView defined in xml
        val classFeedRecyclerView = view.findViewById<RecyclerView>(R.id.class_feed_recycler)
        classFeedRecyclerView.layoutManager = LinearLayoutManager(view.context)
        val classFeedAdapter = MainClassListAdapter(postlist)
        classFeedRecyclerView.adapter = classFeedAdapter

        val progressBar = view.findViewById<ProgressBar>(R.id.class_feed_progress_bar)
        progressBar.visibility = View.VISIBLE

        FirebaseAccessor.getUser(User.curUser.id!!) { exception: Exception?, user: User? ->
            if (exception != null) {
                progressBar.visibility = View.GONE
                Toast.makeText(this.context, "Something goes wrong", Toast.LENGTH_SHORT).show()
            } else {
                if (user != null){
                    User.curUser = user

                    postlist.clear()
                    if (User.curUser.ratings.size == 0) {
                        progressBar.visibility = View.GONE
                    }

                    for (ele in User.curUser.ratings) {
                        FirebaseAccessor.getCourse(ele) { exception: Exception?, course: Course? ->
                            if (exception == null && course != null) {
                                postlist.add(course)
                            }
                            classFeedAdapter.setData(postlist)
                            if (postlist.size == User.curUser.ratings.size) {
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this.context, "Failed to fetch current user.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val addButton = view.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            progressBar.visibility = View.GONE
            findNavController().navigate(R.id.action_classFeedFragment_to_classSelectionFragment)
        }


    }
}
