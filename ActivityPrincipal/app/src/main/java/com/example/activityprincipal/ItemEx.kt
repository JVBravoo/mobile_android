package com.example.activityprincipal

import android.os.Parcelable
import android.net.Uri
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemEx(val resource: Int, val imageuri: Uri? = null, val text1: String?, val text2: String?): Parcelable