package com.android.example.ratemycourse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {
    var courses: MutableList<Course> = mutableListOf()
    var test: MutableList<Message> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test_get_all.setOnClickListener{
            FirebaseAccessor.setupAllCourses { exception, result ->
                if (exception == null) {
                    courses = result
                }
            }
        }

        test_set_user.setOnClickListener{
            User.setupCurUser(null)
        }

        test_add_course.setOnClickListener{
            User.curUser.addCourse(courses[0].id!!) {
                //
            }
        }

        test_post_rate.setOnClickListener{
            User.curUser.postRate(courses[0], 4.0, "Great") {
                //
            }
        }
//        test_cancel_rate.setOnClickListener{
//            User.curUser.cancelRate(courses[0], User.curUser.ratings[0]) {
//                //
//            }
//        }
//
//        test_delete_course.setOnClickListener{
//            User.curUser.removeCourse(courses[0].id!!) {
//                //
//            }
//        }
    }
}