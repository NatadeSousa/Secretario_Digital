package com.example.secretario_digital.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.secretario_digital.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdapterListaDeDizimos(
    private val context: Context?,
    private val dataset: List<String>
) : RecyclerView.Adapter<AdapterListaDeDizimos.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val nome: TextView = view.findViewById(R.id.text_nome)
        val dizimo: TextView = view.findViewById(R.id.text_dizimo)
        val data: TextView = view.findViewById(R.id.text_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dizimo,parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dizimoAtual = dataset[position]

    }

    override fun getItemCount() = dataset.size

}