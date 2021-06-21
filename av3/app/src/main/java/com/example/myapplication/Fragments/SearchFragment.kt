package com.example.myapplication.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    lateinit var searchModel: SearchModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchModel = ViewModelProvider(this).get(SearchModel::class.java)
//        return super.onCreateView(inflater, container, savedInstanceState)

        searchBtn.setOnClickListener {
            searchTemp()
        }
        return // alguma coisa...
    }


    // Checar se essa função funciona...
    // Tenho minhas dúvidas
    // referencia: https://stackoverflow.com/questions/51141970/check-internet-connectivity-android-in-kotlin
    fun isOnline(context: Context): Boolean {

        // diz respeito ao resultado final
        // var final = false

        // Diz respeito ao Connectivity Manager
        // Referencia: https://developer.android.com/reference/kotlin/android/net/ConnectivityManager
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null){
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null){
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    Log.i("Internet", "Transport Cellular")
                    return true
                }
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    Log.i("Internet", "Transport WiFi")
                    return true
                }
            }
        }
        // Senão.. false
        return false
    }

    private fun searchTemp(){
        // Apareceram dois erros de Null-asserted
        // Antes tava: if(!isOnline(context))
        if (!context?.let { isOnline(it) }!!){
            // Fim da etapa 3
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
    }

}

