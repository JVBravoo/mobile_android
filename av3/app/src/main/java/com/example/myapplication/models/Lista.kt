package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

// Representa as primeiras linhas do JSON:
// "message":
// "cod":
// "count":
data class Lista(
    @SerializedName ("list")
    var list: ArrayList<City>,

    @SerializedName("message")
    var message: String,

    @SerializedName("cod")
    var cod: String,

    @SerializedName("count")
    var count: Int,
)
