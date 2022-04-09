package com.android.example.ratemycourse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// TODO: PHASE 2.2 - Define ImageUploadResponse Data Class
//  Stores two variables of type String called message and type
@Parcelize
data class ImageUploadResponse(
    var message: String,
    var path: String
): Parcelable
