package com.github.sahibmobdev.bulletinboardapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding
import com.github.sahibmobdev.bulletinboardapp.dialogs.DialogSpinnerHelper
import com.github.sahibmobdev.bulletinboardapp.utils.CityHelper

class EditAdsAct : AppCompatActivity() {

    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

    }

    //OnClicks
    fun onClickSelectCountry(view:View) {
        val listOfCountry = CityHelper.getAllCountries(this)
        dialog.showSpinnerDialog(this, listOfCountry)
    }
}