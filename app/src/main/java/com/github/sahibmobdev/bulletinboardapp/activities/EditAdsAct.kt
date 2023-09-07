package com.github.sahibmobdev.bulletinboardapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding
import com.github.sahibmobdev.bulletinboardapp.dialogs.DialogSpinnerHelper
import com.github.sahibmobdev.bulletinboardapp.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    lateinit var binding: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listOfCountry = CityHelper.getAllCountries(this)
        val dialog = DialogSpinnerHelper()
        dialog.showSpinnerDialog(this, listOfCountry)
    }
}