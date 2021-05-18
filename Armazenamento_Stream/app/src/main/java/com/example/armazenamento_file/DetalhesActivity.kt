package com.example.armazenamento_file

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.app.Activity
import android.content.ClipData
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_detalhes.*
import java.io.File
import java.io.FileInputStream
import java.lang.Exception


class DetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val item: ItemEx? = intent.getParcelableExtra(MainActivity.MAIN_ACTIVITY_ID)
//        Log.i("fon", item.toString())
        textView.text = item?.nome
        textView2.movementMethod = ScrollingMovementMethod()
        textView3.text = item?.armazenamento

        setSupportActionBar(findViewById(R.id.toolbar));
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = item?.nome;

        if(item?.armazenamento == ItemEx.ARMAZENAMENTO_INTERNO) {
            readInternal(null, item)
        }
        if (item?.armazenamento == ItemEx.ARMAZENAMENTO_EXTERNO) {
            readExternal(null, item)
        }
    }
        // A partir do slide 24
        private fun readInternal(view: View?, Item: ItemEx) {
            try {
                val arq = File(filesDir, Item.nome)
                if(Item.jetpack != ItemEx.NO_JETPACK){
                    val encrypted = jetpackFile(arq, Item.jetpack)
                    encrypted?.openFileInput().use { inputStream -> textView2.text = inputStream?.readBytes()?.decodeToString() }
                }
                else{
                    FileInputStream(arq).use { stream -> textView2.text = stream.bufferedReader().use { it.readText() }
                }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
         private fun readExternal(view: View?, Item: ItemEx) {
             try {
                 val arq = File(getExternalFilesDir(null), Item.nome)

                 if (Item.jetpack != ItemEx.NO_JETPACK){
                     val encrypted = jetpackFile(arq, Item.jetpack)
                     encrypted?.openFileInput().use { inputStream -> textView2.text = inputStream?.readBytes()?.decodeToString() }
                 }
                 else {
                     FileInputStream(arq).use { stream ->
                         textView2.text = stream.bufferedReader().use { it.readText() }
                     }
                 }
             }
             catch (e: Exception) {
                 e.printStackTrace()
             }
         }

    // Slide 36
        private fun jetpackFile(arq: File, encrypt: String): EncryptedFile? {
            val KeyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias =
                MasterKeys.getOrCreate(KeyGenParameterSpec)

            if (encrypt == ItemEx.JETPACK)
                return EncryptedFile.Builder(arq, this, masterKeyAlias, EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build()
            return null
        }
}