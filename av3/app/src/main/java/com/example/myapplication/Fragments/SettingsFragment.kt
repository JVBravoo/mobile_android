package com.example.myapplication.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    lateinit var settingsModel: SettingsModel
    lateinit var prefs: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsModel = ViewModelProvider(this).get(SettingsModel::class.java)

        // view
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        // Type mismatch. adicionei non-null asserted call
        prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)!!
        editor = prefs.edit()


        val saveBtn = root.findViewById<Button>(R.id.save_btn)
        val temperatura = prefs.getString("temperatura", "imperial").toString()
        val linguagem = prefs.getString("linguagem", "en").toString()
        setList(root)


        // Check temperature unit from shared preferences
        val unit_tempo = root.findViewById<RadioGroup>(R.id.radio_group_unidade_temp)
        if (temperatura == "imperial") {
            unit_tempo.check(R.id.radio_group_F)
        }
        else {
            unit_tempo.check(R.id.radio_group_C)
        }
        // Check language from shared preferences
        val lingua = root.findViewById<RadioGroup>(R.id.radio_group_linguagem)
        if (linguagem == "en"){
            lingua.check(R.id.radio_group_ingles)
        }
        else{
            lingua.check(R.id.radio_group_portugues)
        }

//        temperatura.check(settingsModel.temperatura.value!!)
//        linguagem.check(settingsModel.linguagem.value!!)


        saveBtn.setOnClickListener {

            editor.apply {
                putString("temperatura", "imperial")
                apply()
            }

            editor.apply {
                putString("linguagem", "en")
                apply()
            }

        }
//        prefs = storeActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        return super.onCreateView(inflater, container, savedInstanceState)
        return root
    }

    // Salva a preferencia de linguagem escolhida e de métrica da temperatura
//    fun salvarPref(view: View){

        // Se clicada no RadioButton
//        if(view is RadioButton) {
//            when (temperatura.checkedRadioButtonId) {
//                R.id.radio_group_C -> {
//                    settingsModel.Temp(R.id.radio_group_C)
//                    // Referencia para metric vs imperial: https://www.youtube.com/watch?v=B9EWaL0ZuqE
//                    Log.e("settings", "antes do apply: metric")
//                    editor.apply {
//                        putString("temperatura", "metric")
//                        apply()
//                    }
//                }
//                R.id.radio_group_F -> {
//                    settingsModel.Temp(R.id.radio_group_F)
//                    Log.e("settings", "antes do apply: imperial")
//                    editor.apply {
//                        putString("temperatura", "imperial")
//                        apply()
//                    }
//                }
//            }
//        }
//        // Se clicada no RadioButton
//        if (view is RadioButton) {
//            when (linguagem.checkedRadioButtonId) {
//                R.id.radio_group_ingles -> {
//                    settingsModel.Ling(R.id.radio_group_ingles)
//                    editor.apply {
//                        putString("linguagem", "en")
//                        apply()
//                    }
//                }
//                R.id.radio_group_portugues -> {
//                    settingsModel.Ling(R.id.radio_group_portugues)
//                    editor.apply {
//                        putString("linguagem", "pt")
//                        apply()
//                    }
//                }
//            }
//        }
//    }


    fun getList(): SharedPreferences?{
        return this.requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    // Parte 2 de criar o arquivo e persistir os dados em SharedPreferences
    // referência: https://www.youtube.com/watch?v=B9EWaL0ZuqE
    @SuppressLint("CommitPrefEdits")
    fun setList(view: View){

        val prefs: SharedPreferences? = getList()

        val linguagem = view.findViewById<RadioGroup>(R.id.radio_group_linguagem)
        val temperatura = view.findViewById<RadioGroup>(R.id.radio_group_unidade_temp)
//        val linguagem = prefs?.getString("Temperature Unit", null)
//        val temperatura = prefs?.getString("Description Language", null)
        var linguagem_id = 0
        var temperatura_id = 0

        // Faz a checagem da métrica de temperatura do SharedPreferences
        if (temperatura.toString() == "metric"){
            temperatura_id = radio_group_C.id
        }
        else if (temperatura.toString() == "imperial"){
            temperatura_id = radio_group_F.id
        }

        // Faz a checagem da linguagem do SharedPreferences
        if (linguagem.toString() == "en"){
            linguagem_id = radio_group_ingles.id
        }
        else if (linguagem.toString() == "pt"){
            linguagem_id = radio_group_portugues.id
        }

        // Funcao no arquivo SettingsModel.kt
        settingsModel.setList(temperatura_id, linguagem_id)
    }
}