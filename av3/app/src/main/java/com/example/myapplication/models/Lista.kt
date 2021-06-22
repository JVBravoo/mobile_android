package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class Lista(
    @SerializedName ("list")
    var city: List<City>
)
