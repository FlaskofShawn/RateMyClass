package com.android.example.ratemycourse

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class FirebaseAccessor {
    companion object {
        val db : FirebaseFirestore = setupDB()

        val TAG = "FirebaseAccessor"

        fun setupDB() : FirebaseFirestore {
                val db = Firebase.firestore
                val settings = FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(false)
                        .build()
                db.firestoreSettings = settings
                return db
        }

        fun createUser(userID: String, email: String, completion: ((Exception?) -> Unit)?) {
            getUser(userID) { exception, user ->
                if (exception == null) {
                    if (user == null) {  // user does not exist on firestore
                        db.collection("users").document(userID).set(hashMapOf("email" to email))
                                .addOnSuccessListener {
                                    if (completion != null) {
                                        completion(null)
                                    }
                                }
                                .addOnFailureListener {e ->
                                    if (completion != null) {
                                        completion(e)
                                    }
                                }
                    }

                    // user already exists
                    if (completion != null) {
                        completion(null)
                    }
                } else if (completion != null) {
                    completion(exception)
                }
            }
        }

        fun getUser(userID: String, completion: (Exception?, User?) -> Unit) {
            db.collection("users").document(userID)
                    .get()
                    .addOnSuccessListener { result ->
                        val user = toObject<User>(result)
                        completion(null, user)
                    }
                    .addOnFailureListener { exception ->
                        completion(exception, null)
                        Log.w(TAG, "Error getting user.", exception)
                    }
        }

        /**
         * Get the information of all courses. Returns null exception and a list of courses on
         * success. Returns a non-null exception and an empty list on failure.
         * onAddedListener - this callback is called in the following two situations:
             - Initial call: returns the a list of courses
             - Subsequent calls: returns the updated version list of all the courses of a user
                whenever a change in the courses happens

         * Usage Note: You only need to set up all courses once. Update the local copy of
         * the list of all the courses in "onAddedListener" and update the UI accordingly.
         */
        fun setupAllCourses(onAddedListener: (Exception?, MutableList<Course>) -> Unit) {
            db.collection("courses")
                .addSnapshotListener { snapshot, exception ->
                    var courses: MutableList<Course> = mutableListOf()
                    if (snapshot != null) {
                        for (doc in snapshot.documents ) {
                            toObject<Course>(doc)?.let { courses.add(it) }
                            Log.d(TAG, "${doc.id} => ${doc.data}")
                        }
                    }
                    if (exception != null) {
                        Log.w(TAG, "Error getting all courses", exception)
                    } else {
                        Log.d(TAG, "Successfully get all courses")
                    }
                    onAddedListener(exception, courses)
                }
        }

        /**
         * Get a single course. Returns null exception and a non-null course on success. Returns
         * a non-null exception and null course on failure.
         */
        fun getCourse(courseID: String, completion: (Exception?, Course?) -> Unit) {
            db.collection("courses").document(courseID)
                    .get()
                    .addOnSuccessListener { result ->
                            completion(null, toObject<Course>(result))
                        Log.d(TAG, "Successfully get a course.")
                    }
                    .addOnFailureListener { exception ->
                        completion(exception, null)
                        Log.w(TAG, "Error getting course.", exception)
                    }
        }

        /** The following functions are for internal use */

        fun addCourseForUser(userID: String, courseID: String, completion: ((Exception?) -> Unit)?) {
            db.collection("users").document(userID)
                    .update("ratings", FieldValue.arrayUnion(courseID))
                    .addOnSuccessListener {
                        if (completion != null) {
                            completion(null)
                        }
                    }
                    .addOnFailureListener {exception ->
                        if (completion != null) {
                            completion(exception)
                        }
                    }
        }

        fun postRateForUser(course: Course,
                            score: Double, comment: String, completion: ((Exception?) -> Unit)?) {
            val newScore = integrateNewScore(course, score)
            if (newScore != null && course.id != null) {
                val courseRef = db.collection("courses").document(course.id!!)

                db.runBatch { batch ->
                    batch.update(courseRef, hashMapOf("score" to newScore, "rateCount" to (course.rateCount!! + 1)))
                    batch.update(courseRef,"comments", FieldValue.arrayUnion(comment))
                }
                .addOnSuccessListener {
                    Log.d(TAG, "Successfully posted/updated a rating")
                    if (completion != null) {
                        completion(null)
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error posting/updating a rating", it)
                    if (completion != null) {
                        completion(it)
                    }
                }
            }
        }

        private fun integrateNewScore(course: Course, newRate: Double): Double? {
            if (course.score != null && course.rateCount != null) {
                return (course.rateCount!! * course.score!! + newRate) / (course.rateCount!! + 1)
            }
            return null
        }

        private inline fun <reified T> toObject(doc: DocumentSnapshot?) : T? {
            var result = doc?.toObject<T>()
            (result as Identifiable?)?.id = doc?.id
            return result
        }
    }
}