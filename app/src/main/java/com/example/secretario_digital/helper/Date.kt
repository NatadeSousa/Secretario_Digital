package com.example.secretario_digital.helper

import android.util.Log
import com.example.secretario_digital.R
import com.example.secretario_digital.RegistroDeDizimoFragment
import java.util.*

private val c = Calendar.getInstance()

//public fun getCurrentMonth(currentMonth: String): String{
//        when(currentMonth){
//            "01" -> return "janeiro"
//            "02" -> return "fevereiro"
//            "03" -> return "março"
//            "04" -> return "abril"
//            "05" -> return "maio"
//            "06" -> return "junho"
//            "07" -> return "julho"
//            "08" -> return "agosto"
//            "09" -> return "setembro"
//            "10" -> return "outubro"
//            "11" -> return "novembro"
//            "12" -> return "dezembro"
//        }
//        return "Ocorreu um erro"
//}

public fun isDayRight(currentDay: Int, currentMonth: Int, currentYear: Int): Boolean {
    when (currentMonth) {

        1 -> if (currentDay <= 31) return true
        2 -> {
            //Caso o ano seja bissexto, o usuário poderá informar o dia 29 no mês de fevereiro
            if (currentYear % 400 == 0) {
                if (currentDay <= 29) return true
            }
            if (currentYear % 100 == 0) {
                if (currentDay <= 28) return true
            }
            if (currentYear % 4 == 0) {
                if (currentDay <= 29) return true
            } else {
                if (currentDay <= 28) return true
            }
        }
        3 -> if (currentDay <= 31) return true
        4 -> if (currentDay <= 30) return true
        5 -> if (currentDay <= 31) return true
        6 -> if (currentDay <= 30) return true
        7 -> if (currentDay <= 31) return true
        8 -> if (currentDay <= 31) return true
        9 -> if (currentDay <= 30) return true
        10 -> if (currentDay <= 31) return true
        11 -> if (currentDay <= 30) return true
        12 -> if (currentDay <= 31) return true
    }
    return false
}

