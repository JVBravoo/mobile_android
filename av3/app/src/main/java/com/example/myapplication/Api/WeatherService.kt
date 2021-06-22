package com.example.myapplication.Api

import com.example.myapplication.models.Lista
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    // Requisição
    @GET("find")
    fun findResponse(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("appid") apiKey: String,
    ): Call<Lista>
}