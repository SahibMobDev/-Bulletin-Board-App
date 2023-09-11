package com.github.sahibmobdev.bulletinboardapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.sahibmobdev.bulletinboardapp.R
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
        if (binding.tvCity.text.toString() != getString(R.string.select_city)) {
            binding.tvCity.text = getString(R.string.select_country)
        }
        dialog.showSpinnerDialog(this, listOfCountry, binding.tvCountry)
    }

    fun onClickSelectCity(view:View) {
        val selectedCountry = binding.tvCountry.text.toString()
        if (selectedCountry != getString(R.string.select_country)) {
            val listOfCities = CityHelper.getAllCities(selectedCountry,this)
            dialog.showSpinnerDialog(this, listOfCities, binding.tvCity)
        } else {
            Toast.makeText(this, "Choice country first", Toast.LENGTH_SHORT).show()
        }
    }
}