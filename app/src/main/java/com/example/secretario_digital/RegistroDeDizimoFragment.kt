package com.example.secretario_digital

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.os.HandlerCompat.postDelayed
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
import com.example.secretario_digital.helper.isNetworkAvailable
import com.example.secretario_digital.helper.showBottomDialog
import java.lang.String.valueOf


class RegistroDeDizimoFragment : Fragment() {
    private var _binding: FragmentRegistroDeDizimoBinding? = null
    private val binding get() = _binding!!

    private val KEY_DATE = "date"
    private val MSG = "RegistroDeDizimo"

    private val dizimoAtual = Dizimo()


    private lateinit var databaseReference: DatabaseReference
    private lateinit var layoutBtnRegistrar: LinearLayout
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
            //Limpando FILE_PREFERENCES caso seja a primeira vez que eu tenha aberto esse fragment
            if(nomeDizimista == "null" || nomeDizimista == "Nome"){
                val sharedPreferences = context?.getSharedPreferences("FILE_PREFERENCES",
                    MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.clear()
                editor?.apply()
            }
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

        val sharedPreferences = context?.getSharedPreferences("FILE_PREFERENCES", MODE_PRIVATE)

        val spData = sharedPreferences?.getString(KEY_DATE, "spData")

        if (spData != "spData" && spData != "null" && spData != null) {
            editData.setText(spData.toString())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val sharedPrefs = context?.getSharedPreferences("FILE_PREFERENCES", MODE_PRIVATE)
        val editor = sharedPrefs?.edit()

    }

    private fun setClicks() {
        btnVoltar.setOnClickListener {
            val action =
                RegistroDeDizimoFragmentDirections.actionRegistroDeDizimoFragmentToListaDeDizimos()
            findNavController().navigate(action)
        }

        btnNome.setOnClickListener {
            //Verificando se os campos de dízimo e data já foram preenchidos, para então salvar
            //o dízimo e data caso o usuário tenha clicado para selecionar o dizimista depois
            if(editData.text.toString().isNotEmpty()){
                val sharedPreferences = context?.getSharedPreferences("FILE_PREFERENCES", MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putString(KEY_DATE,editData.text.toString())
                editor?.apply()
            }else{
                val sharedPreferences = context?.getSharedPreferences("FILE_PREFERENCES", MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.clear()
                editor?.apply()
            }

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

        if(isNetworkAvailable(requireContext())){
            if ((nome.isNotEmpty()) && (nome != getString(R.string.word_nome))) {
                if (dizimoDouble >= 1.00) {
                    if (data.isNotEmpty()) {
                        if (data.length == 10) {

                            btnRegistrar.visibility = View.GONE
                            layoutBtnRegistrar.setPadding(20,20,20,20)
                            pbRegistroDeDizimo.visibility = View.VISIBLE

                            with(dizimoAtual){
                                dataDizimo = data
                                valorDizimo = dizimo
                                nomeDizimista = nome
                            }

                            saveData()
                        } else {
                            showBottomDialog(message = R.string.text_data_invalida_dizimo)
                        }
                    } else {
                        showBottomDialog(message = R.string.text_data_dizimo)
                    }
                } else {
                    showBottomDialog(message = R.string.text_valor_dizimo)
                }
            } else {
                showBottomDialog(message = R.string.text_nome_dizimista)
            }
        }else{
            showBottomDialog(message = R.string.text_conexao_internet)
        }

    }

    private fun saveData() {

        val nomeFormatado = dizimoAtual.nomeDizimista?.replace(".", "")
        databaseReference
            .child("dizimos")
            .child("Nome: $nomeFormatado")
            .setValue(dizimoAtual).addOnCompleteListener(requireActivity()){ task ->
                if(task.isSuccessful){ //VALIDAR O ERRO DE QUANDO O USUÁRIO NÃO ESTIVER CONECTADO À INTERNET
                    layoutBtnRegistrar.setPadding(0,0,0,0)
                    pbRegistroDeDizimo.visibility = View.GONE
                    btnRegistrar.visibility = View.VISIBLE
                    showBottomDialog(message = R.string.text_dizimo_registrado)
                    findNavController().navigate(R.id.action_registroDeDizimoFragment_to_listaDeDizimos)
                }else{
                    Toast.makeText(context, "Tente novamente mais tarde!", Toast.LENGTH_SHORT).show()
                    layoutBtnRegistrar.setPadding(0,0,0,0)
                    pbRegistroDeDizimo.visibility = View.GONE
                    btnRegistrar.visibility = View.VISIBLE
                }
            }
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
        layoutBtnRegistrar = binding.layoutBtnRegistrar
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