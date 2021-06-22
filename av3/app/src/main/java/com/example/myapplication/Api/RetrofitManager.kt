package com.example.myapplication.Api

// We are also going to use Gson. This is a library used to convert java objects
// into a JSON format. But in our case, we will be converting JSON to java object.
// We will be getting data from the network in a JSON format and through GSON converter
// we will convert it to an object we can use in our application.

import com.example.myapplication.models.Lista
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
//    internal fun getService(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("http://api.openweathermap.org/data/2.5/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //    fun getService(): WeatherService = this.instance.create(WeatherService::class.java)
    fun getService(): WeatherService? {
        return RetrofitManager().retrofit.create(WeatherService::class.java)
    }
}