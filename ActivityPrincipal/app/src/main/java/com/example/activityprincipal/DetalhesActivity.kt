package com.example.activityprincipal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item_ex.view.*

class DetalhesActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val item: ItemEx? = intent.getParcelableExtra("Item_click") // Pega o item que foi clickado
        val position = intent.getIntExtra("position",0) // Pega a posição do item clickado
        val imageView:ImageView = findViewById(R.id.imageView)
        // null check

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = item?.text1

        if (item?.imageuri != null) {
            imageView.setImageURI(item?.imageuri) // Pega a URI da imagem
        }

        val fruta_nome: TextView = findViewById(R.id.nome_fruta)
        fruta_nome.text = item?.text1 // Relaciona o nome da fruta com o textview1
        val descricao_fruta: TextView = findViewById((R.id.descricao_fruta))
        descricao_fruta.text = item?.text2

        val remove = findViewById<Button>(R.id.remove_fruit)
        remove.setOnClickListener {
            val register = Intent()
            register.putExtra("position", position)
            setResult(Activity.RESULT_OK, register)
            finish()
        }
    }
}