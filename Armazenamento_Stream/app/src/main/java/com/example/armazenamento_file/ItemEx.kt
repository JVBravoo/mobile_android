package com.example.armazenamento_file

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemEx (var nome: String, var content: String, var armazenamento:String, var jetpack: String): Parcelable {
    companion object{
        const val ARMAZENAMENTO_INTERNO = "Internal"
        const val ARMAZENAMENTO_EXTERNO = "External"
        const val JETPACK = "Jetpack"
        const val NO_JETPACK = "Not Jetpack"
    }
}