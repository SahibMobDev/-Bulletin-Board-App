package com.github.sahibmobdev.bulletinboardapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding

class EditAdsAct : AppCompatActivity() {
    lateinit var binding: ActivityEditAdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}