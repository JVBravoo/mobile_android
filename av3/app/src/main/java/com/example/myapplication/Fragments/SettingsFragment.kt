package com.example.myapplication.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
        // Fiquei em dúvida se essa linha ficava aqui ou dentro da funcao setList()


        val saveBtn = root.findViewById<Button>(R.id.save_btn)
        val temperatura = prefs.getString("temperatura", "celsius").toString()
        val linguagem = prefs.getString("linguagem", "english").toString()
        setList()


        // Check temperature unit from shared preferences
        val unit_tempo = root.findViewById<RadioGroup>(R.id.radio_group_unidade_temp)
        if (temperatura == "F") {
            unit_tempo.check(R.id.radio_group_F)
        }
        else {
            unit_tempo.check(R.id.radio_group_C)
        }
        // Check language from shared preferences
        val lingua = root.findViewById<RadioGroup>(R.id.radio_group_linguagem)
        if (linguagem == "English"){
            lingua.check(R.id.radio_group_ingles)
        }
        else{
            lingua.check(R.id.radio_group_portugues)
        }

//        temperatura.check(settingsModel.temperatura.value!!)
//        linguagem.check(settingsModel.linguagem.value!!)


        saveBtn.setOnClickListener {
            // Colocar funcao de escolha de temperatura e linguagem abaixo
//            it is an implicit name of a single parameter in lambda function
            salvarPref(it)
        }
//        prefs = storeActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
//        return super.onCreateView(inflater, container, savedInstanceState)
        return root
    }

    // Salva a preferencia de linguagem escolhida e de métrica da temperatura
    fun salvarPref(view: View){
        val temperatura = radio_group_unidade_temp
        val linguagem = radio_group_linguagem

        // Se clicada no RadioButton
        if(view is RadioButton) {
            when (temperatura.checkedRadioButtonId) {
                R.id.radio_group_C -> {
                    settingsModel.Temp(R.id.radio_group_C)
                    // Referencia para metric vs imperial: https://www.youtube.com/watch?v=B9EWaL0ZuqE
                    editor.apply {
                        putString("temperature unit", "metric")
                        apply()
                    }
                }
                R.id.radio_group_F -> {
                    settingsModel.Temp(R.id.radio_group_F)
                    editor.apply {
                        putString("temperature unit", "imperial")
                        apply()
                    }
                }
            }
        }
        // Se clicada no RadioButton
        if (view is RadioButton) {
            when (linguagem.checkedRadioButtonId) {
                R.id.radio_group_ingles -> {
                    settingsModel.Ling(R.id.radio_group_ingles)
                    editor.apply {
                        putString("description language", "english")
                        apply()
                    }
                }
                R.id.radio_group_portugues -> {
                    settingsModel.Ling(R.id.radio_group_portugues)
                    editor.apply {
                        putString("description language", "portuguese")
                        apply()
                    }
                }
            }
        }
    }


//    fun getList(context: Context): SharedPreferences?{
//        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
//    }

    // Parte 2 de criar o arquivo e persistir os dados em SharedPreferences
    // referência: https://www.youtube.com/watch?v=B9EWaL0ZuqE
    @SuppressLint("CommitPrefEdits")
    fun setList(){
        // Tive um problema com o context abaixo, e tive que colocar a "?"... Não entendi mto bem pq
        val prefs: SharedPreferences? =
            requireActivity().getSharedPreferences("settings", 0)
        val linguagem = prefs?.getString("Temperature Unit", null)
        val temperatura = prefs?.getString("Description Language", null)
        var linguagem_id = 0
        var temperatura_id = 0

        // Métrica de temperatura
        if (temperatura.toString() == "metric"){
            temperatura_id = radio_group_C.id
        }
        else if (temperatura.toString() == "imperial"){
            temperatura_id = radio_group_F.id
        }

        // Língua
        if (linguagem.toString() == "english"){
            linguagem_id = radio_group_ingles.id
        }
        else if (linguagem.toString() == "portuguese"){
            linguagem_id = radio_group_portugues.id
        }

        // Funcao no arquivo SettingsModel.kt
        settingsModel.setList(temperatura_id, linguagem_id)
    }
}