package com.example.secretario_digital.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.secretario_digital.ListaDeMembrosDirections
import com.example.secretario_digital.RegistroDeDizimoFragment
import com.example.secretario_digital.R
import com.example.secretario_digital.RegistroDeDizimoFragmentDirections
import org.w3c.dom.Text

class AdapterTraining(
    private val context: Context?,
    private val dataset: List<String>
) : RecyclerView.Adapter<AdapterTraining.ItemViewHolder>(){
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.text_nome_dizimista)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_membro, parent,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: String = dataset[position]
        holder.textView.text = item
        holder.itemView.setOnClickListener {
            val action = ListaDeMembrosDirections.actionListaDeMembrosToRegistroDeDizimoFragment(item)
            findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount() = dataset.size
}