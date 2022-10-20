package com.example.secretario_digital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.secretario_digital.adapter.AdapterListaDeDizimos
import com.example.secretario_digital.databinding.FragmentListaDeDizimosBinding
import com.example.secretario_digital.helper.FirebaseHelper

class ListaDeDizimos : Fragment() {

    private var _binding: FragmentListaDeDizimosBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerView: RecyclerView
    lateinit var btnDizimo: AppCompatImageButton

    private lateinit var dataSet: List<String>

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
        setRv()
        setClicks()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillDataset(){
//        FirebaseHelper.getDatabase()
//            .child("dizimos")
    }

    //DESCOBRIR COMO RECUPERAR DADOS DO BANCO DE DADOS E PASSAR PRA LISTA DO ADAPTER
    private fun setRv(){
//        recyclerView.adapter = AdapterListaDeDizimos(requireContext(), )
//        recyclerView.setHasFixedSize(true)

    }

    private fun setClicks(){
        btnDizimo.setOnClickListener {
            findNavController().navigate(R.id.action_listaDeDizimos_to_registroDeDizimoFragment)
        }
    }

    private fun referComponents(){
        btnDizimo = binding.btnDizimo
    }



}