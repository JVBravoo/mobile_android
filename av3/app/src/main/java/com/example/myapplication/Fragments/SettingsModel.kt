package com.example.myapplication.Fragments

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import androidx.fragment.app.commit
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.view.*

// Referencia da MutableLiveData(): https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData

class SettingsModel : ViewModel(){
    var temperatura = MutableLiveData<Int>()
    var linguagem = MutableLiveData<Int>()
    //
    var _temperatura = 0
    var _linguagem = 0

    fun Temp(id:Int){
        _temperatura = id
        temperatura.value = _temperatura
    }

    fun Ling(id:Int){
        _linguagem = id
        linguagem.value = _linguagem
    }

    fun setSettings(unidade_tempo: Int, ling: Int){
        if (unidade_tempo == 0){
            temperatura = MutableLiveData<Int>().apply {
                value = R.id.radio_group_C
            }
        }
        else{
            Temp(unidade_tempo)
        }

        if (ling == 0){
            linguagem = MutableLiveData<Int>().apply {
                value = R.id.radio_group_ingles
            }
        }
        else {
            Ling(ling)
        }
    }
}