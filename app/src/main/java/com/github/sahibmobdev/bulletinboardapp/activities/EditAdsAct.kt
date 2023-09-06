package com.github.sahibmobdev.bulletinboardapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding
import com.github.sahibmobdev.bulletinboardapp.utils.CityHelper

class EditAdsAct : AppCompatActivity() {
    lateinit var binding: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, CityHelper.getAllCountries(this))

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCountry.adapter = adapter
    }
}