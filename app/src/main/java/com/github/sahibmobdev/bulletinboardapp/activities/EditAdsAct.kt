package com.github.sahibmobdev.bulletinboardapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.adapters.ImageAdapter
import com.github.sahibmobdev.bulletinboardapp.data.Advert
import com.github.sahibmobdev.bulletinboardapp.database.DbManager
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding
import com.github.sahibmobdev.bulletinboardapp.dialogs.DialogSpinnerHelper
import com.github.sahibmobdev.bulletinboardapp.fragments.FragmentCloseInterface
import com.github.sahibmobdev.bulletinboardapp.fragments.ImageListFragment
import com.github.sahibmobdev.bulletinboardapp.utils.CityHelper
import com.github.sahibmobdev.bulletinboardapp.utils.ImageManager
import com.github.sahibmobdev.bulletinboardapp.utils.ImagePicker

class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {

    var chooseImageFrag: ImageListFragment? = null
    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
    private val dbManager = DbManager()
    val imageAdapter: ImageAdapter by lazy { ImageAdapter() }
    var editImagePos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        ImagePicker.showSelectedImages(resultCode, requestCode, data, this)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.getImages(this, 3, ImagePicker.REQUEST_CODE_GET_IMAGES)
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
        binding.vpImages.adapter = imageAdapter
    }

    //OnClicks
    fun onClickSelectCountry(view: View) {
        val listOfCountry = CityHelper.getAllCountries(this)
        if (binding.tvCity.text.toString() != getString(R.string.select_city)) {
            binding.tvCity.text = getString(R.string.select_country)
        }
        dialog.showSpinnerDialog(this, listOfCountry, binding.tvCountry)
    }

    fun onClickSelectCity(view: View) {
        val selectedCountry = binding.tvCountry.text.toString()
        if (selectedCountry != getString(R.string.select_country)) {
            val listOfCities = CityHelper.getAllCities(selectedCountry, this)
            dialog.showSpinnerDialog(this, listOfCities, binding.tvCity)
        } else {
            Toast.makeText(this, "Choice country first", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickSelectCategory(view: View) {

            val listOfCategory = resources.getStringArray(R.array.category).toMutableList() as ArrayList
            dialog.showSpinnerDialog(this, listOfCategory, binding.tvCat)

    }

    fun onClickGetImages(view: View) {
        val list = imageAdapter.mainArray
        if (list.size == 0) {
            ImagePicker.getImages(this, 3, ImagePicker.REQUEST_CODE_GET_IMAGES)
        } else {
            openChooseImageFragment(null)
            chooseImageFrag?.updateAdapterFromEdit(list)
        }
    }

    fun onClickPublish(view: View) {

        dbManager.publishAd(fillAd())
    }

    fun fillAd(): Advert {
        val ad: Advert
        binding.apply {
            ad = Advert(tvCountry.text.toString(),
                tvCity.text.toString(),
                editTel.text.toString(),
                edIndex.text.toString(),
                checkBoxWithSend.isChecked.toString(),
                tvCat.text.toString(),
                edPrice.text.toString(),
                edDescription.text.toString(),
                dbManager.db.push().key)
        }
        return ad
    }

    override fun onFragClose(list: ArrayList<Bitmap>) {
        binding.scrollViewMine.visibility = View.VISIBLE
        imageAdapter.update(list)
        chooseImageFrag = null
    }

    fun openChooseImageFragment(newList: ArrayList<String>?) {
        chooseImageFrag = ImageListFragment(this, newList)
        binding.scrollViewMine.visibility = View.GONE
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.place_holder, chooseImageFrag!!).commitAllowingStateLoss()
    }
}