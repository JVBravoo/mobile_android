package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

// Parâmetros que estavam no JSON:
//"id":
//"main":
//"description":
//"icon":
data class Weather(

    @SerializedName("id")
    var id: Int,

    @SerializedName("main")
    var main: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("icon")
    var icon: String,
)
