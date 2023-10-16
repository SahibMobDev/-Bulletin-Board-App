package com.github.sahibmobdev.bulletinboardapp.database

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager {
    val database = Firebase.database("https://bulletinboardapp-c8623-default-rtdb.europe-west1.firebasedatabase.app/")
    val db = database.getReference("main")

    fun publishAd() {
        db.setValue("Hello")
    }
}