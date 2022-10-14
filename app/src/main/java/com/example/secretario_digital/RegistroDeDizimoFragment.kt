package com.example.secretario_digital

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.secretario_digital.model.Dizimo
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.marcoscg.currencyedittext.CurrencyEditText
import com.santalu.maskara.widget.MaskEditText
import com.example.secretario_digital.R
import com.example.secretario_digital.databinding.FragmentRegistroDeDizimoBinding


class RegistroDeDizimoFragment : Fragment() {
    private var _binding: FragmentRegistroDeDizimoBinding? = null
    private val binding get() = _binding!!

    private val KEY_DATE = "date"
    private val KEY_VALUE = "value"
    private val KEY_VALUE1 = "valuee"
    private val MSG = "RegistroDeDizimo"

    private val dizimoAtual = Dizimo()

    private val sharedPrefs =
        context?.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)

    private lateinit var databaseReference: DatabaseReference
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
        setBtnNome()
        activateTextListeners()
        setClicks()

        val edito1 = sharedPrefs?.edit()
        edito1?.putString(KEY_VALUE,"testing")
        edito1?.apply()
        var editado1 = sharedPrefs?.getString(KEY_VALUE,"CHUCRUTI")
        //IDE TAVA COM UM BUG. PROBLEMA NO SHARED RESOLVIDO DEIXANDO A VARIÁVEL Q FAZ REFERENCIA
        //A ELE AQUI NO MESMO ESCOPO DO EDITOR
        Log.i(MSG,"Shared: $editado1")

//        val spData = sharedPreferences?.getString(KEY_DATE,"spData")
//        val spDizimo = sharedPreferences?.getString(KEY_VALUE,"spDizimo")

//        if(spData != "spData" && spData != "null" && spData != null){
//            editDizimo.setText(spData.toString())
//        }
//        if(spDizimo != "spDizimo" && spData != "null" && spData != null){
//            editData.setText(spDizimo.toString())
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClicks() {
        btnVoltar.setOnClickListener {
            val action =
                RegistroDeDizimoFragmentDirections.actionRegistroDeDizimoFragmentToListaDeDizimos()
            findNavController().navigate(action)
        }

        btnNome.setOnClickListener {

            //Verificando se os campos de dízimo e data já foram preenchidos, para então salvar
            // o dízimo e data caso o usuário tenha clicado para selecionar o dizimista depois
            val editor = sharedPrefs?.edit()
            if(editDizimo.text.toString().isNotEmpty()){
                editor?.putString(KEY_VALUE,editDizimo.text.toString())
                editor?.apply()
                val shared = sharedPrefs?.getString(KEY_VALUE,"0")
                Log.i(MSG,"Shared:  $shared")
            }
            if(editData.text.toString().isNotEmpty()){
                editor?.putString(KEY_DATE,editData.text.toString())
                editor?.apply()
            }

            val action =
                findNavController().navigate(R.id.action_registroDeDizimoFragment_to_listaDeMembros)
        }

        btnRegistrar.setOnClickListener {
            hideKeyboard(requireContext(),requireView())
            validateData()
        }
    }

    private fun activateTextListeners() {

        //Listener para o EditText de valor do dízimo
        editDizimo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //Pegando o valor de p0 e passando para inteiro
                val helper = editDizimo.getNumericValue()

                //Quando o valor for diferente de 0, modificar a cor do texto
                if (helper != 0.0) {
                    editDizimo.setTextColor(Color.parseColor("#FFFFFFFF"))
                }

                //Quando o valor informado for maior do que 1, modificar o StartIconDrawable
                if (helper >= 1.00) {
                    color = Color.parseColor("#268847")
                    inputLayout2.setStartIconDrawable(R.drawable.ic_checked)
                    inputLayout2.setStartIconTintList(ColorStateList.valueOf(color))
                } else {
                    color = Color.parseColor("#C50077")
                    inputLayout2.setStartIconDrawable(R.drawable.ic_small_arrow)
                    inputLayout2.setStartIconTintList(ColorStateList.valueOf(color))
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        //Listener para o EditText da data em que foi realizado o dízimo
        editData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! == 10) {
                    color = Color.parseColor("#268847")
                    inputLayout3.setStartIconDrawable(R.drawable.ic_checked)
                    inputLayout3.setStartIconTintList(ColorStateList.valueOf(color))
                } else {
                    color = Color.parseColor("#C50077")
                    inputLayout3.setStartIconDrawable(R.drawable.ic_small_arrow)
                    inputLayout3.setStartIconTintList(ColorStateList.valueOf(color))
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    private fun validateData() {
        val nome = btnNome.text.toString()
        val dizimo = editDizimo.text.toString().trim()
        val dizimoDouble = editDizimo.getNumericValue()
        val data = editData.text.toString().trim()

        if ((nome.isNotEmpty()) && (nome != getString(R.string.word_nome))) {
            if (dizimoDouble >= 1.00) {
                if (data.isNotEmpty()) {
                    if (data.length == 10) {

                        btnRegistrar.visibility = View.INVISIBLE
                        pbRegistroDeDizimo.visibility = View.VISIBLE

                        with(dizimoAtual){
                            dataDizimo = data
                            valorDizimo = dizimo
                            nomeDizimista = nome
                        }

                        saveData()
                    } else {
                        Toast.makeText(
                            context, "Informe uma data válida!", Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context, "Informe a data!", Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    context, "Informe um valor válido para o dízimo!", Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(context, "Informe seu nome!", Toast.LENGTH_LONG).show()
        }

    }

    private fun saveData() {
        val nomeFormatado = dizimoAtual.nomeDizimista?.replace(".", "")
        databaseReference
            .child("dizimos")
            .child("Nome: $nomeFormatado")
            .setValue(dizimoAtual).addOnCompleteListener(requireActivity()){ task ->
                //ADJUST THREAD.SLEEP
                if(task.isSuccessful){
                    Thread.sleep(1300)
                    Toast.makeText(context, "Dízimo registrado!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registroDeDizimoFragment_to_listaDeDizimos)
                }else{
                    Toast.makeText(context, "Tente novamente mais tarde!", Toast.LENGTH_SHORT).show()
                }
            }
        pbRegistroDeDizimo.visibility = View.GONE
        btnRegistrar.visibility = View.VISIBLE

    }

    private fun setBtnNome() {
        if ((nomeDizimista != getString(R.string.word_null)) && (nomeDizimista != getString(R.string.word_nome))) {
            btnNome.text = nomeDizimista
            color = Color.parseColor("#FFFFFF")
            btnNome.setTextColor(color)
            btnNome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checked, 0, 0, 0)
        } else {
            btnNome.text = getString(R.string.word_nome)
            color = Color.parseColor("#3C4B68")
            btnNome.setTextColor(color)
            btnNome.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_small_arrow, 0, 0, 0)
        }
    }

    private fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && inputMethodManager.isActive) {
            //inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun referComponents() {
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