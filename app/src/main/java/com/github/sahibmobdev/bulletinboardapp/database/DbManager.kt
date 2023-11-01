package com.github.sahibmobdev.bulletinboardapp.database

import android.util.Log
import com.github.sahibmobdev.bulletinboardapp.data.Advert
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DbManager(val readDataCallback: ReadDataCallback?) {
    private val database = Firebase.database("https://bulletinboardapp-c8623-default-rtdb.europe-west1.firebasedatabase.app/")
    val db = database.getReference("main")
    val auth = Firebase.auth

    fun publishAd(advert: Advert) {
        if (auth.uid != null) {
            db.child(advert.key ?: "empty").child(auth.uid!!).child("ad").setValue(advert)
        }
    }

    fun readDataFromDb() {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adArray = ArrayList<Advert>()
                for (item in snapshot.children) {
                    val ad = item.children.iterator().next().child("ad").getValue(Advert::class.java)
                    if (ad != null) {
                        adArray.add(ad)
                    }
                }
                readDataCallback?.readData(adArray)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}