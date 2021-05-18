package com.example.armazenamento_file

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.RadioButton
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import java.io.*


class MainActivity : AppCompatActivity(), adapter.OnItemCLick{

//    var recycleradapter = adapter(this)
    var lista = ArrayList<ItemEx>()
    var textView: EditText? = null
//    var button: Button? = null
//    var read_button: Button? = null
    var textView2: EditText? = null
    var arq_storage = ItemEx.ARMAZENAMENTO_INTERNO
    var jetpack = ItemEx.NO_JETPACK
//    var textView3: TextView? = null
//    lateinit var recyclerView: RecyclerView

    companion object {
        const val MAIN_ACTIVITY_ID = "MAIN_ACTIVITY_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Exercício Arm. Interno e Externo"
        val recycler = findViewById<View>(R.id.recycler) as RecyclerView

//        val first: Array<String> = this.fileList()
//        lista.addAll(first)

        // Caso o checkBox do armazenamento interno seja selecionado...
//        val radioGroupInt = findViewById<RadioButton>(R.id.internal)
//        radioGroupInt.setOnClickListener {
////            lista.clear()
//
////            val arquivosinternos: Array<String> = this.fileList()
////            lista.addAll(arquivosinternos)
//            recycler.adapter?.notifyDataSetChanged()
//        }
//
//        val radioGroupExt = findViewById<RadioButton>(R.id.external) // CheckBox do armazenamento externo
//
//        // Caso o checkBox do armazenamento externo seja selecionado...
//        radioGroupExt.setOnClickListener {
//
//            val arquivosexternos: Array<out File>? = this.getExternalFilesDirs(null)?.clone() // Segui os slides
//            val listaexterna: List<String>? = arquivosexternos?.map { it.name }
//
//            if(listaexterna != null){
//                lista.addAll(listaexterna.toTypedArray())
//            }
//            lista.clear()
//            recycler.adapter?.notifyDataSetChanged()
//        }


        val buttonCreate = findViewById<Button>(R.id.button)
        buttonCreate.setOnClickListener {
            textView = findViewById<TextView>(R.id.textView) as EditText? // Talvez precise mudar isso
            textView2 = findViewById<TextView>(R.id.textView2) as EditText?

            val nome = textView?.text.toString() // Isso também
            val content = textView2?.text.toString()

            val memoryint = internal.isChecked // método que checa a CheckBox, para saber se foi selecionado ou não

            val jetpack = findViewById<CheckBox>(R.id.jetpack) // Pega o ID do XML checkbox

            // Seguindo os slides 35
            if (jetpack.isChecked){
                if (memoryint) this.filesDir
                else this.getExternalFilesDir(null)

                val masterKeyAlias = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

                val file = File(filesDir, nome)
                if(file.exists()){
                    file.delete()
                }

                // Salvar o arquivo utilizando JetPack, por isso o Encrypted...
                val encryptedFile = EncryptedFile.Builder(this, file, masterKeyAlias, EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build()
                encryptedFile.openFileOutput().use { writer -> writer.write(content.toByteArray()) }
                Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show()
            }
            else {
                // Para salvar na memória interna
                if (memoryint){
                    this.openFileOutput(nome, Context.MODE_PRIVATE).use{
                        it.write(content.toByteArray())
                    }
                    Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show()
                }
                else {
                    // retirado do slide 26
                    // Para salvar os arquivos na memória externa
                    val extFile = File(this.getExternalFilesDir(null), nome)
                    val outputStream = FileOutputStream(extFile)
                    outputStream.use { stream -> stream.write(content.toByteArray()) }
                    Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show()
                }
            }
            // Para adicionar apenas o nome dos arquivos no recyclerView
            lista.add(ItemEx(nome, content, arq_storage, ItemEx.NO_JETPACK))
            recycler.adapter?.notifyItemInserted(lista.size)
        }
        recycler.adapter = adapter(lista, this)
        recycler.layoutManager = LinearLayoutManager(this)
    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.id) {
                R.id.internal ->
                    if (checked) {
                        arq_storage = ItemEx.ARMAZENAMENTO_INTERNO
                    }
                R.id.external ->
                    if (checked) {
                        arq_storage = ItemEx.ARMAZENAMENTO_EXTERNO
                    }
            }
        }
    }

    // Consegui implementar o Delete, mas quando precisei adicionar a segunda tela tive problemas e acabei não conseguindo entregar tudo
    // Quando eu consegui implementar os dois, aconteceu que quando eu clicava no item, ele deletava tudo e depois abria a "DetalhesActivity" vazia.
    // Preferi priorizar a tela de "DetalhesActivity", pois acho que vale mais pontos que a função de deletar.

//    override fun onDelete(item: ItemEx){
//        val radioGroupInt = findViewById<RadioButton>(R.id.internal)
//        val nome = lista[position] // Pensar em como vou passar para a atividade de detalhes...
//
//        if (radioGroupInt.isChecked){
//            this.deleteFile(arq_storage) // Deleta o nome do arquivo que foi selecionado, no caso do interno
//        }
//        else {
//            val extFile = File(this.getExternalFilesDir(null), nome)
//            extFile.delete() // Deleta o nome do arquivo que foi selecionado, no caso do externo
//        }
//        lista.removeAt(position) // Remove o arquivo na posição que ele se encontrava
//        val recycler = findViewById<View>(R.id.recycler) as RecyclerView
//    }

    override fun onItemClick(position: Int){
        val intent = Intent(this, DetalhesActivity::class.java)
        val itemClick = lista[position]
        intent.putExtra(MAIN_ACTIVITY_ID, itemClick)

        startActivity(intent)
    }
}