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
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Api.RetrofitManager
import com.example.myapplication.R
import com.example.myapplication.models.Lista
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    lateinit var prefs: SharedPreferences
    lateinit var searchModel: SearchModel
    private val apiKey = "732d823624e403a30804eab140f918f6"
    private var adapter = Adapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchModel = ViewModelProvider(this).get(SearchModel::class.java)

        prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)!!

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        val searchBtn = view.findViewById<Button>(R.id.searchBtn)
//        val progressBar = root.findViewById<ProgressBar>(R.id.progressbar)

        searchBtn.setOnClickListener {
            val temperatura = prefs.getString("temperatura", "metric").toString()
            val linguagem = prefs.getString("linguagem", "en").toString()
            Log.e("fon", temperatura.toString())
            Log.e("fon", linguagem.toString())
            val city = searchTxt?.text.toString()
            progressbar.setVisibility(View.VISIBLE) // To show the ProgressBar
            getfindResponse(city, temperatura, linguagem)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)
        searchModel.searchLista.observe(requireActivity(), Observer { adapter.submitList(it) })
    }

    private fun getfindResponse(city: String, temperatura: String, linguagem: String) {
//        progressbar.setVisibility(View.INVISIBLE) // To hide the ProgressBar

        val servico = RetrofitManager().getService()
        val cidades: Call<Lista>? = servico?.findResponse(
            city,
            temperatura,
            linguagem,
            apiKey
        )

        // tratamento da requisição
            cidades!!.enqueue(object : Callback<Lista> {
                override fun onResponse(
                    call: Call<Lista>,
                    response: Response<Lista>
                ) {
                    progressbar.setVisibility(View.INVISIBLE) // To hide the ProgressBar
                    val city: Lista? = response.body()
                    Toast.makeText(activity, city.toString(), Toast.LENGTH_SHORT).show()

                    // adapter
                    if (city != null) {
                        searchModel.updateCity(city.list)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<Lista>, t: Throwable) {
                    progressbar.setVisibility(View.INVISIBLE) // To hide the ProgressBar
                    Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
                    // Se caso não funcionar
                    // Tivemos que usar o CLEARTEXT opção 1 pra resolver
                    Log.e("fon", t.toString())
                }
            }  )

    }



    // referencia: https://stackoverflow.com/questions/51141970/check-internet-connectivity-android-in-kotlin
    // Etapa 3 - caso o device nao esteja conectado
    fun isOnline(context: Context): Boolean {
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

