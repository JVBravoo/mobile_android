package com.example.activityprincipal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class MainActivity : AppCompatActivity(), Adapter_ex.OnItemClickListener {

    private var exampleList = generateList(7) // quantidade de itens na lista

    private var adapter = Adapter_ex(exampleList, this) // Gerencia os dados e fornece a view secundária para ele, serve como uma ponte (estrutura de dados e o recyclerView)

    companion object{
        const val MAIN_LIST = "MAIN_LIST"
        const val MAIN_ACTIVITY_REGISTER_CODE = 1
        const val MAIN_ACTIVITY_NOT_REGISTER_CODE = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Bundle?
        if(savedInstanceState != null) {
            exampleList = savedInstanceState.getParcelableArrayList<ItemEx>(MAIN_LIST) as ArrayList<ItemEx>
            adapter = Adapter_ex(exampleList, this) // precisei chamar a função novamente
        }

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        FloatingButton.setOnClickListener {
            val register = Intent(this, SecondActivity::class.java) // Intent para ir para a segunda activity
            startActivityForResult(register, MAIN_ACTIVITY_REGISTER_CODE)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) { // Função para fazer com que os dados não se percam quando a tela girar
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putParcelableArrayList(MAIN_LIST, exampleList) // Faz com que a minha lista e as últimas alterações fiquem mesmo com a tela girando

    }

    fun insertItem(view: View?, Item: ItemEx) { // Insere os itens
        val index = 0
        exampleList.add(index, Item)
        adapter.notifyItemInserted(index) // index diz onde nós queremos adicionar o item
    }

    fun removeItem(view: View?, index:Int){ // Remove os itens
        exampleList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }


    override fun onItemClick(position: Int) {
        val intent = Intent(this, DetalhesActivity::class.java)
        val Item_click = exampleList[position] // "Pega" o item da posição onde ele foi clicado
        intent.putExtra("Item_click", Item_click) // getExtra no detalhes Activity
        intent.putExtra("position", position) // Faz com que a posição certa seja removida, anteriormente estava removendo o primeiro da lista.
        startActivityForResult(intent, MAIN_ACTIVITY_NOT_REGISTER_CODE)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == MAIN_ACTIVITY_REGISTER_CODE){
                val Item: ItemEx? = data?.getParcelableExtra(MAIN_LIST)
                val nome_fruta = Item?.text1 // Faz a relação do EditText com o ItemEx (text1, nesse caso) devolvendo pra o Main.
                val descricao_fruta = Item?.text2
                val imageuri = Item?.imageuri
                if (Item != null) {
                    insertItem(null,Item) // Insere o item caso seja diferente de null.
                }
            }
            if(requestCode == MAIN_ACTIVITY_NOT_REGISTER_CODE){
                val index: Int? = data?.getIntExtra("position", 0)
                // Type mismatch, Surround with null-check (o IF foi obrigatório)
                if (index != null) {
                    removeItem(null, index) // Remove o item caso seja diferente de null.
                }
            }
        }
    }

    private fun generateList(size: Int): ArrayList<ItemEx>{

        val lista = ArrayList<ItemEx>()

        for(i in 0 until size) {
            val item = ItemEx(R.drawable.ic_pera,null, "Item $i", "Descrição: ") // Usei a foto de uma pera para exemplificar no start da activity
            lista += item // adiciona os itens acima na lista

        }
        return lista
    }
}