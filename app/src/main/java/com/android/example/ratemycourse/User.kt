package com.android.example.ratemycourse

import android.app.Activity
import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.parcel.Parcelize
import java.lang.Exception

@Parcelize
class User : Parcelable, Identifiable {
    override var id: String? = null
    var ratings: MutableList<String> = mutableListOf()

    companion object {
        var curUser: User = User()

        /** Fetch the current user's information.
         * completion - user information fetching callback (normally set to null)
         * onAddedListener - this callback is called in the following two situations:
            - Initial call: returns the "ratedCourses" field of a user
            - Subsequent calls: returns the updated version of the "ratedCourses" field of a user
                whenever a change in this field happens

         * Usage Note: Update the "ratedCourses" field of your local instance of the user and update
         * the UI accordingly in onAddedListener is recommended instead of inside the completion
         * handlers for POST and DELETE functions.
        */
        fun setupCurUser(completion: ((Exception?) -> Unit)?) {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser == null) {
                Log.d("User", "Fireauth user is null")
                return
            }

            // create user if needed
            FirebaseAccessor.createUser(currentUser.uid, currentUser.email!!) {exception ->
                if (exception != null) {
                    if (completion != null) {
                        completion(exception)
                    }
                } else {
                    // get current user
                    FirebaseAccessor.getUser(currentUser.uid) { exception, user ->
                        if (user != null) {
                            curUser = user
                        }
                        if (completion != null) {
                            completion(exception)
                        }
                    }
                }
            }

        }
    }

    /**
     * Add a course for the user.
     * completion is nullable
     */
    fun addCourse(courseID: String, completion: ((Exception?) -> Unit)?) {
        if (id != null) {
            FirebaseAccessor.addCourseForUser(id!!, courseID, completion)
        } else {
            Log.d("User", "User id is null")
        }
    }

    /**
     * Post a course rating for the user. If the course rating already exists,
     * update the rating with the new score. Update the average of score of the course.
     * completion is nullable
     */
    fun postRate(course: Course, score: Double, comment: String, completion: ((Exception?) -> Unit)?) {
        if (id != null) {
            FirebaseAccessor.postRateForUser(course, score, comment, completion)
        } else {
            Log.d("User", "User id is null")
        }
    }

    /**
     * Cancel a course rating for the user. Update the average of score of the course.
     * completion is nullable
     */
//    fun cancelRate(course: Course, rate: Rate, completion: ((Exception?) -> Unit)?) {
//        if (id != null) {
//            FirebaseAccessor.cancelRateForUser(id!!, course, rate, completion)
//        } else {
//            Log.d("User", "User id is null")
//        }
//    }

    /**
     * Delete a course for the user.
     * completion is nullable
     */
//    fun removeCourse(courseID: String, completion: ((Exception?) -> Unit)?) {
//        if (id != null) {
//            FirebaseAccessor.removeCourseForUser(id!!, courseID, completion)
//        } else {
//            Log.d("User", "User id is null")
//        }
//    }
}
