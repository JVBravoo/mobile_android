package com.example.myapplication.Fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.City

class SearchModel : ViewModel(){
    var searchLista = MutableLiveData<ArrayList<City>>()
    var lista = ArrayList<City>()

    internal fun updateCity(city: ArrayList<City>){
        lista.clear()
        lista = city
        searchLista.value = lista
    }
}