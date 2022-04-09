package com.android.example.ratemycourse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(
        override var id: String? = null,
        var subject: String? = null,
        var number: Int? = null,
        var suffix: String? = null,
        var name: String? = null,
        var description: String? = null,
        var score: Double? = null,
        var rateCount: Int? = null,
        var comments: MutableList<String> = mutableListOf()
) : Parcelable, Identifiable