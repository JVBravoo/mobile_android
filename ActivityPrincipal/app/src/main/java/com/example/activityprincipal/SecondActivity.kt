package com.example.activityprincipal

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

//    lateinit var imageView: ImageView
//    lateinit var button: Button
    companion object{
        const val ACTIVITY_SECOND_IMAGE = "ACTIVITY_SECOND_IMAGE"
    }

    private val pickImage = 100
    var image: Uri? = null

    private val title = "Título"
    private val descricao = "Descrição"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Adicionar Fruta"

        //Surrounded with null-check, Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Bundle?
//        if (savedInstanceState != null) {
//            image = savedInstanceState.getParcelable(ACTIVITY_SECOND_IMAGE)
//            imagem.setImageResource()
//        }

        val adiImage = findViewById<Button>(R.id.buttonLoadPicture) // botão para adicionar imagem (Load image)
        adiImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI) // Abre a galeria
            startActivityForResult(gallery, pickImage) // permissao para abrir galeria
        }

//        imageView = findViewById(R.id.buttonLoadPicture)

        val loadinfo = findViewById<Button>(R.id.loadinfo)
            loadinfo.setOnClickListener {
                val intent = Intent() // Intent para salvar da segunda activity para a Main
                val nomeFruta = findViewById<EditText>(R.id.nome_fruta)
                val descricaoFruta = findViewById<EditText>(R.id.descricao_fruta)
                val fruta = nomeFruta.text.toString();
                val descricao = descricaoFruta.text.toString()
                val item = ItemEx(R.drawable.ic_pera, image, fruta, descricao)
                intent.putExtra(MainActivity.MAIN_LIST, item) // Por isso uso o putExtra, para receber o salvamento depois na Main.
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) { // Função para fazer com que os dados não se percam quando a tela girar
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == pickImage){
            image = data?.data // Pega o URI da imagem

//             Para dar permissão para buscar a URI, pois ocorria de crashar a aplicação
            val content = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            image?.let { content.takePersistableUriPermission(it, takeFlags) }
            imageView.setImageURI(image)
        }
    }
}