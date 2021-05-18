package com.example.armazenamento_file

import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class adapter(private val lista: ArrayList<ItemEx>, private val listener: MainActivity): RecyclerView.Adapter<adapter.ArqViewHolder>() {

    // Estava com erro no listener (de não conseguir chamar a função que estava na main) na linha 10 e resolvi usando "interface", mas não consegui implementar junto com a segunda tela
    interface OnDeleteListener{
        fun onDelete(position: Int)
    }

    interface OnItemCLick{
        fun onItemClick(position: Int)
    }

    inner class ArqViewHolder(item: View): RecyclerView.ViewHolder(item), View.OnClickListener {

        val imageView: ImageView = item.findViewById(R.id.deleta)
        val titulo: TextView = item.findViewById(R.id.titulo)

        init {
            imageView.setOnClickListener(this)
            item.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val index = adapterPosition
            var deleta = v?.id == R.id.deleta
            if (index != RecyclerView.NO_POSITION){
                if (deleta) {
                    listener.onDelete(index)
                }
                else {
                    listener.onItemClick(index)
//                listener.onDelete(index)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArqViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ArqViewHolder(item)
    }

    override fun onBindViewHolder(holder: ArqViewHolder, position: Int) {
        val novoItem = lista[position]
        holder.titulo.text = novoItem.nome
    }

    override fun getItemCount() = lista.size

}