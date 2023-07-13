package com.example.testapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrResponse(
    val photos: Photos
): Parcelable