package com.example.mediastore


import android.Manifest
import android.content.ClipData
import android.content.ContentUris
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.os.Bundle
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.recyclerview.widget.GridLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val adapt = Adapter()
    var imageList = ArrayList<Item>()

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Armazenamento compartilhado" // Fazer com que a Toolbar fique com o título indicado no exercício

        requestPermissions()
        AddImages()
        adapt.imageContent(imageList)
        recycler_view.adapter = adapt
        recycler_view.layoutManager = GridLayoutManager(this, 2) // Coloquei o LayoutManager também no XML
        recycler_view.setHasFixedSize(true)
    }

    // link da documentação: https://developer.android.com/training/data-storage/shared/media
    private fun AddImages() {
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        applicationContext.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong((idColumn))
                val title = cursor.getString(nameColumn)
                var item = Item(
                    title,
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id))
                    imageList.add(item)
            }
        }
    }

    // Runtime permission | Slide 48
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }
    // link da documentação: https://developer.android.com/training/permissions/requesting
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE)
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
    }
}
