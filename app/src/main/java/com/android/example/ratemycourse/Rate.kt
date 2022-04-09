package com.android.example.ratemycourse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rate : Parcelable, Identifiable {
    override var id: String? = null  // the course id that is rated

    var pending: Boolean? = null
    var userEmail: String? = null
    var score: Double? = null
    var comment: String? = null
}