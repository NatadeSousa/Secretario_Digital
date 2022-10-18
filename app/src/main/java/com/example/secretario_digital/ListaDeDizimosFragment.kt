package com.example.secretario_digital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.fragment.findNavController
import com.example.secretario_digital.databinding.FragmentListaDeDizimosBinding

class ListaDeDizimos : Fragment() {

    private var _binding: FragmentListaDeDizimosBinding? = null
    private val binding get() = _binding!!

    lateinit var btnDizimo: AppCompatImageButton

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
        setClicks()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun setClicks(){
        btnDizimo.setOnClickListener {
            val action = ListaDeDizimosDirections.actionListaDeDizimosToRegistroDeDizimoFragment("Nome")
            findNavController().navigate(action)
        }
    }

    private fun referComponents(){
        btnDizimo = binding.btnDizimo
    }



}