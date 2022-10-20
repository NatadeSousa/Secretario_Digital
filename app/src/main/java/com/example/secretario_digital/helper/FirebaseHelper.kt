package com.example.secretario_digital.helper

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object{

        fun getDatabase() = FirebaseDatabase.getInstance().reference

    }

}