package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This Activity already has an action bar supplied by the window decor.
        // Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.
        // Lembrar de sempre colocar no tema "NoActionBar"
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Files"

        // Slide 71
        val prefs = getSharedPreferences("nome", Context.MODE_PRIVATE)

        // Slide 72
        button_Write.setOnClickListener {

            val nome = Nome.text.toString()
            val idade = Idade.text.toString()?.toInt()
            val editor = prefs.edit()
            editor.apply {
                putInt("Idade_user", idade)
                putString("Nome_user", nome)
                apply()
            }
        }

        // Slide 73
        button_Read.setOnClickListener {
            val Idade_user = prefs.getInt("Idade_user", 0)
            val Nome_user = prefs.getString("Nome_user", null)
//          show_text.text = "O seu nome é ${prefs.getString("Nome_user", null)} e a sua idade é ${prefs.getString("Idade_user", 0.toString())}"
            show_text.text = "O seu nome é ${Nome_user} e a sua idade é ${Idade_user}"
        }

    }

}