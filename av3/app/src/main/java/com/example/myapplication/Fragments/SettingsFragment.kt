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

    private var unidade_temp: String = ""
    private var linguagem_type: String = ""
    private var temperatura: String = "temperatura"
    private var linguagem: String = "linguagem"
    private var celsius: String = "metric"
    private var fahr: String = "imperial"
    private var portugues: String = "pt"
    private var ingles: String = "en"
//    lateinit var prefs: SharedPreferences

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        iniciarRbutton(root)
        iniciarButton(root)
        return root
    }

    private fun iniciarButton(view: View) {

        // botoes de temperatura
        val temperatura_c = view.findViewById<Button>(R.id.radio_group_C)
        temperatura_c.setOnClickListener { unidade_temp = celsius }
        val temperatura_f = view.findViewById<Button>(R.id.radio_group_F)
        temperatura_f.setOnClickListener { unidade_temp = fahr }


        // botoes da linguagem
        val linguagem_en = view.findViewById<Button>(R.id.radio_group_ingles)
        linguagem_en.setOnClickListener { linguagem_type = ingles }
        val linguagem_pt = view.findViewById<Button>(R.id.radio_group_portugues)
        linguagem_pt.setOnClickListener { linguagem_type = portugues }

        val save_btn = view.findViewById<Button>(R.id.save_btn)
        save_btn.setOnClickListener {
            val prefs: SharedPreferences = getList()
            prefs.edit().apply {
                putString(temperatura, unidade_temp)
                putString(linguagem, linguagem_type)
                apply()
            }
        }
    }


    private fun iniciarRbutton(view: View) {
        val prefs: SharedPreferences = getList()

        // Check temperature unit from shared preferences
        val radiounidadeTemp = view.findViewById<RadioGroup>(R.id.radio_group_unidade_temp)

        unidade_temp = prefs.getString(temperatura, celsius).toString()

        if (unidade_temp == fahr) {
            radiounidadeTemp.check(R.id.radio_group_F)
        } else {
            radiounidadeTemp.check(R.id.radio_group_C)
        }

        // Check description language from shared preferences
        val radioLinguagem = view.findViewById<RadioGroup>(R.id.radio_group_linguagem)

        linguagem_type = prefs.getString(linguagem, ingles).toString()

        if (linguagem_type == portugues) {
            radioLinguagem.check(R.id.radio_group_portugues)
        } else {
            radioLinguagem.check(R.id.radio_group_ingles)
        }
    }


    private fun getList(): SharedPreferences {
        return this.requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }
}