package com.example.activityprincipal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView


class Adapter_ex(private val exampleList : List<ItemEx>, private val listener: OnItemClickListener) : RecyclerView.Adapter<Adapter_ex.ExampleViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ex,
            parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {

        val currentItem = exampleList[position]

        holder.imageView.setImageURI(currentItem.imageuri)

        holder.textView1.text = currentItem.text1 // Título
        holder.textView2.text = currentItem.text2 // Descrição

    }

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView1: TextView = itemView.findViewById(R.id.text_title)
        val textView2: TextView = itemView.findViewById(R.id.text_description)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    override fun getItemCount() = exampleList.size
}