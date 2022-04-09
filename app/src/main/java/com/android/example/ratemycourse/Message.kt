package com.android.example.ratemycourse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// TODO: PHASE 2.2 - Define Message Data Class
//  Stores a variable of type String called message
@Parcelize
data class Message(
    var message: String
): Parcelable