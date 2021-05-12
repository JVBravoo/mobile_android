package com.example.armazenamento_file

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    var textView: EditText? = null
    var button: Button? = null
    var read_button: Button? = null
    var textView2: TextView? = null
    var textView3: TextView? = null
//    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Exercício Arm. Interno e Externo"

        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        button = findViewById(R.id.button)
        read_button = findViewById(R.id.read_button)
        textView3 = findViewById(R.id.textView3)

        button?.setOnClickListener(View.OnClickListener { writeFile() })
        read_button?.setOnClickListener(View.OnClickListener { readFile() })
    }

    fun writeFile() {
        val textToSave = textView!!.text.toString()
        try {
            val fileOutputStream = openFileOutput("Armazenamento.txt", MODE_PRIVATE)
            fileOutputStream.write(textToSave.toByteArray())
            fileOutputStream.close()
            Toast.makeText(applicationContext, "Arquivo salvo", Toast.LENGTH_SHORT).show()

            textView!!.setText("") // Para que depois de escrever e apertar no botão, ele apagar o que tinha antes.
            textView2!!.setText("")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFile() {
        try {
            val fileInputStream = openFileInput("Armazenamento.txt")
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuffer = StringBuffer()

            var lines: String? = null
            while (bufferedReader.readLine().also { lines = it } != null) {
                stringBuffer.append("""$lines""".trimIndent())
            }
            textView3!!.text = stringBuffer.toString() // Mostrar no TextView abaixo.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}