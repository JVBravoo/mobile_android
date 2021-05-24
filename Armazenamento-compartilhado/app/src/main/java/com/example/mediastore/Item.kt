package com.example.mediastore

import android.net.Uri
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
@Parcelize
data class Item(var Imagetitle: String, var ImageUri: Uri?): Parcelable