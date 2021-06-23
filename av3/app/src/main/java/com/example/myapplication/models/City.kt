package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class City(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("main")
    var main: Main,

    @SerializedName("weather")
    var weather: List<Weather>,
)


