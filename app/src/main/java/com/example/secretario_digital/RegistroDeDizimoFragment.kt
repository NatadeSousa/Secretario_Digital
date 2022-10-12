package com.example.secretario_digital

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.marcoscg.currencyedittext.CurrencyEditText
import com.santalu.maskara.widget.MaskEditText
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.secretario_digital.databinding.FragmentRegistroDeDizimoBinding
import com.example.secretario_digital.model.Dizimo
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.String.valueOf

class RegistroDeDizimoFragment : Fragment() {
    private var _binding: FragmentRegistroDeDizimoBinding? = null
    private val binding get() = _binding!!

    private val MSG: String = "RegistroDeDizimo"
    private lateinit var databaseReference: DatabaseReference
    private val dizimoAtual = Dizimo()
    lateinit var inputLayout2: TextInputLayout
    lateinit var inputLayout3: TextInputLayout
    private lateinit var editDizimo: CurrencyEditText
    private lateinit var editData: MaskEditText
    private lateinit var pbRegistroDeDizimo: ProgressBar
    private lateinit var btnVoltar: AppCompatImageButton
    private lateinit var btnNome: AppCompatButton
    private lateinit var btnRegistrar: AppCompatButton
    private lateinit var nomeDizimista: String

    var color: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            nomeDizimista = it.getString("nome").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegistroDeDizimoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        referComponents()
        activateTextListeners()
        setClicks()

        if(nomeDizimista != null){
            Log.i(MSG, "NomeDizimista é: $nomeDizimista")
        }
        btnNome.text = nomeDizimista.toString()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClicks(){
        btnVoltar.setOnClickListener{
            val action = RegistroDeDizimoFragmentDirections.actionRegistroDeDizimoFragmentToListaDeDizimos()
            findNavController().navigate(action)
        }

        btnNome.setOnClickListener {
            val action = RegistroDeDizimoFragmentDirections.actionRegistroDeDizimoFragmentToListaDeMembros()
            findNavController().navigate(action)
        }
    }

    private fun activateTextListeners(){

        //Listener para o EditText de nome do dizimista
        btnNome.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Não é necessário
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(p0?.length!! >= 6){
                    color = Color.parseColor("#268847")
                    btnNome.setTextColor(color)
                    btnNome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_small_arrow_green,0,0,0)
                }else{
                    color = Color.parseColor("#C50077")
                    btnNome.setTextColor(color)
                    btnNome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_small_arrow,0,0,0)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Não é necessário
            }
        })

        //Listener para o EditText de valor do dízimo
        editDizimo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //Pegando o valor de p0 e passando para inteiro
                val helper = editDizimo.getNumericValue()

                //Quando o valor for diferente de 0, modificar a cor do texto
                if(helper != 0.0){
                    editDizimo.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                //Quando o valor informado for maior do que 1, modificar o StartIconDrawable
                if(helper >= 1.00){
                    color = Color.parseColor("#268847")
                    inputLayout2.setStartIconDrawable(R.drawable.ic_checked)
                    inputLayout2.setStartIconTintList(ColorStateList.valueOf(color))
                }else{
                    color = Color.parseColor("#C50077")
                    inputLayout2.setStartIconDrawable(R.drawable.ic_small_arrow)
                    inputLayout2.setStartIconTintList(ColorStateList.valueOf(color))
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //Listener para o EditText da data em que foi realizado o dízimo
        editData.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Não é necessário
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length!! == 10 ){
                    color = Color.parseColor("#268847")
                    inputLayout3.setStartIconDrawable(R.drawable.ic_checked)
                    inputLayout3.setStartIconTintList(ColorStateList.valueOf(color))
                }else{
                    color = Color.parseColor("#C50077")
                    inputLayout3.setStartIconDrawable(R.drawable.ic_small_arrow)
                    inputLayout3.setStartIconTintList(ColorStateList.valueOf(color))
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Não é necessário
            }

        })

    }

    private fun validateData(){
        val nome = btnNome.text.toString()
        val dizimo = editDizimo.text.toString().trim()
        val dizimoDouble = editDizimo.getNumericValue()
        val data = editData.text.toString().trim()

        if(nome.isNotEmpty()){
            if(nome.length>6) {
                if(dizimoDouble >= 1.00) {
                    if (data.isNotEmpty()) {
                        if(data.length == 10) {

                            btnRegistrar.visibility = View.INVISIBLE
                            pbRegistroDeDizimo.visibility = View.VISIBLE

                            dizimoAtual.dataDizimo = data
                            dizimoAtual.valorDizimo = dizimo
                            dizimoAtual.nomeDizimista = nome
                            saveData()
                        }else{
                            Toast.makeText(context,"Informe uma data válida!",Toast.LENGTH_LONG).show()
                        }
                    }else {
                        Toast.makeText(context, "Informe a data!", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(context, "Informe um valor válido para o dízimo!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"Informe um nome válido!",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(context,"Informe seu nome!",Toast.LENGTH_LONG).show()
        }

    }

    private fun saveData(){
        val nomeFormatado = dizimoAtual.nomeDizimista?.replace(".","")
        databaseReference
            .child("dizimos")
            .child("Nome: $nomeFormatado")
            .setValue(dizimoAtual)
        pbRegistroDeDizimo.visibility = View.GONE
        btnRegistrar.visibility = View.VISIBLE
        Toast.makeText(context,"Dízimo registrado!",Toast.LENGTH_SHORT).show()
    }

    private fun referComponents(){
        databaseReference = Firebase.database.reference
        inputLayout2 = binding.inputLayout2
        inputLayout3 = binding.inputLayout3
        editDizimo = binding.editDizimo
        editData = binding.editData
        pbRegistroDeDizimo = binding.pbRegistroDeDizimos
        btnVoltar = binding.include2.btnVoltar
        btnNome = binding.btnNome
        btnRegistrar = binding.btnRegistrar
    }

}