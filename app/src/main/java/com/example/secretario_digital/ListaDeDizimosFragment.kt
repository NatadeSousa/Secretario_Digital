package com.example.secretario_digital

import android.os.Bundle
import android.text.SpannableString.valueOf
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.secretario_digital.adapter.AdapterListaDeDizimos
import com.example.secretario_digital.databinding.FragmentListaDeDizimosBinding
import com.example.secretario_digital.helper.FirebaseHelper
import com.example.secretario_digital.helper.showBottomDialog
import com.example.secretario_digital.model.Dizimo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.lang.String.valueOf

class ListaDeDizimos : Fragment() {

    private val KEY_FRAGMENT = "ListaDeDizimosFragment"

    private var _binding: FragmentListaDeDizimosBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerView: RecyclerView
    lateinit var btnDizimo: AppCompatImageButton
    private lateinit var textInfoDizimo: TextView
    private lateinit var pbListaDeDizimos: ProgressBar

    lateinit var dizimoRecebido: Dizimo

    private val listaDeDizimos = mutableListOf<Dizimo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaDeDizimosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        referComponents()
        fillDataset()
        setClicks()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillDataset(){

        FirebaseHelper.getDatabase()
            .child("dizimos")

            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(year in snapshot.children){
                            for(month in year.children){
                                for(person in month.children) {
                                    dizimoRecebido = person.getValue<Dizimo>()!!
                                    listaDeDizimos.add(dizimoRecebido)
                                }
                            }
                        }
                        listaDeDizimos.reverse()
                        setRv()
                    }else{
                        showBottomDialog(message = R.string.text_nenhum_dizimo)
                    }
                    modifyInterface()
                }

                override fun onCancelled(error: DatabaseError) {
                    showBottomDialog(message = R.string.erro_db)
                    modifyInterface()
                }
            })

    }

    private fun setRv(){
        recyclerView.adapter = AdapterListaDeDizimos(requireContext(), listaDeDizimos)
        recyclerView.setHasFixedSize(true)
    }

    private fun modifyInterface(){
        if(listaDeDizimos.isNotEmpty()){
            textInfoDizimo.visibility = View.GONE
        }else{
            textInfoDizimo.text = getString(R.string.text_nenhum_dizimo)
        }
        pbListaDeDizimos.visibility = View.GONE

    }

    private fun setClicks(){
        btnDizimo.setOnClickListener {
            findNavController().navigate(R.id.action_listaDeDizimos_to_registroDeDizimoFragment)
        }
    }



    private fun referComponents(){

        recyclerView = binding.rvListaDeDizimos
        btnDizimo = binding.btnDizimo
        textInfoDizimo = binding.textInfoDizimo
        pbListaDeDizimos = binding.pbListaDeDizimo

    }



}