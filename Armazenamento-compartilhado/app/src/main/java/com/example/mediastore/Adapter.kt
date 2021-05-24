package com.example.mediastore


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item.view.*

class Adapter(): RecyclerView.Adapter<Adapter.ArmazenamentoHolder>() {
    private var imagesList: ArrayList<Item> = ArrayList()

    fun imageContent(content: ArrayList<Item>) {
        this.imagesList = content
    }

    class ArmazenamentoHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ImageURI: ImageView = itemView.image_view
        val ImageTXT: TextView = itemView.image_text_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ArmazenamentoHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ArmazenamentoHolder(itemView)
    }

    override fun onBindViewHolder(holder: Adapter.ArmazenamentoHolder, position: Int) {
        val item = imagesList[position]
        holder.ImageURI?.setImageURI(item.ImageUri)
        holder.ImageTXT?.text = item.Imagetitle
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }


}