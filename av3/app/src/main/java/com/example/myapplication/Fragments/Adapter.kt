package com.example.myapplication.Fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.City
import kotlinx.android.synthetic.main.activity_card.view.*
import com.bumptech.glide.Glide

// Class 'Adapter' is not abstract and does not implement
// abstract base class member public abstract fun onBindViewHolder(holder: Adapter.Holder, position: Int):
// Unit defined in androidx.recyclerview.widget.ListAdapter
// Extender de ListAdapter
// Slide 165
class Adapter(): ListAdapter<City, Adapter.Holder>(callBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val item = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_card, parent, false
        )
        return Holder(item)
    }

    //  Criar DiffCallback e extender de DiffUtil.ItemCallback
    //  Slide 165
    class callBack : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(item: View): RecyclerView.ViewHolder(item){
        // Variáveis do XML cardview
        val cityName: TextView = item.city_name
        val tempText: TextView = item.text_temp
        val appIcon: ImageView = item.image_view_app

        fun linkTo(city: City){
            cityName.text = city.name
            tempText.text = city.main.temp.toString()
            val url = "http://openweathermap.org/img/wn/${city.weather[0].icon}@4x.png\""

            // Tentei aplicar o item no primeiro appIcon, mas tava dando algum problema, apliquei o icone e pegou. Vai saber...
            // O glide tive que colocar a versão, pq tava com um problema de run_closure_doCall
            Glide.with(appIcon.context).load(url).into(appIcon)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.linkTo(getItem(position))
    }
}