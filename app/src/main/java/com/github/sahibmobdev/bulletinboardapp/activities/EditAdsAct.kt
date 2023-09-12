package com.github.sahibmobdev.bulletinboardapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding
import com.github.sahibmobdev.bulletinboardapp.dialogs.DialogSpinnerHelper
import com.github.sahibmobdev.bulletinboardapp.utils.CityHelper
import com.github.sahibmobdev.bulletinboardapp.utils.ImagePicker

class EditAdsAct : AppCompatActivity() {

    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && resultCode == ImagePicker.REQUEST_CODE_GET_IMAGES) {
            if (data != null) {
                val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                Log.d("MyLog2", "Image: ${returnValue?.get(0)}")
                Log.d("MyLog2", "Image: ${returnValue?.get(1)}")
                Log.d("MyLog2", "Image: ${returnValue?.get(2)}")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   ImagePicker.getImages(this)
                } else {

                    Toast.makeText(
                        this,
                        "Approve permissions to open Pix ImagePicker",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
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

    fun onClickGetImages(view: View) {
        ImagePicker.getImages(this)
    }
}