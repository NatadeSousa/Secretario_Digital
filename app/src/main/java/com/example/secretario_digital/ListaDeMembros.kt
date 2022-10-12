package com.example.secretario_digital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.secretario_digital.adapter.AdapterListaDeMembros
import com.example.secretario_digital.data.Membros
import com.example.secretario_digital.databinding.FragmentListaDeDizimosBinding
import com.example.secretario_digital.databinding.FragmentListaDeMembrosBinding

class ListaDeMembros : Fragment() {

    private var _binding: FragmentListaDeMembrosBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnVoltar: AppCompatImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaDeMembrosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        referComponents()
        setRecyclerView()
        setClicks()

    }
    private fun setRecyclerView(){
        val dataSet = Membros().loadData()
        recyclerView.adapter = AdapterListaDeMembros(context, dataSet)
        recyclerView.setHasFixedSize(true)
    }

    private fun setClicks(){
        btnVoltar.setOnClickListener {
            val action = ListaDeMembrosDirections.actionListaDeMembrosToRegistroDeDizimoFragment("Nome")
            findNavController().navigate(action)
        }
    }

    private fun referComponents(){
        recyclerView = binding.rvListaDeMembros
        btnVoltar = binding.include4.btnVoltar
    }

}


