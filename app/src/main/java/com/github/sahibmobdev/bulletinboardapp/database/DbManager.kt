package com.github.sahibmobdev.bulletinboardapp.database

import android.util.Log
import com.github.sahibmobdev.bulletinboardapp.data.Advert
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager {
    private val database = Firebase.database("https://bulletinboardapp-c8623-default-rtdb.europe-west1.firebasedatabase.app/")
    val db = database.getReference("main")
    val auth = Firebase.auth

    fun publishAd(advert: Advert) {
        if (auth.uid != null) {
            db.child(advert.key ?: "empty").child(auth.uid!!).child("ad").setValue(advert)
        }
    }
}