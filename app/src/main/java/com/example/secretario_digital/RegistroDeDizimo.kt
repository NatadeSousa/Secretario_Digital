package com.example.secretario_digital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.cottacush.android.currencyedittext.CurrencyInputWatcher
import com.google.android.material.textfield.TextInputEditText
import secretario_digital.R
import java.text.NumberFormat
import java.util.*
import java.util.logging.Level.parse

class RegistroDeDizimo : AppCompatActivity() {

    final val TAG = "RegistroDeDizimo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_registro_de_dizimo)

        val editValor = findViewById<TextInputEditText>(R.id.edit_dizimo)
        editValor.addTextChangedListener(CurrencyInputWatcher(editValor,"R$ ", Locale("pt","BR")))
    }
}