package com.github.sahibmobdev.bulletinboardapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.github.sahibmobdev.bulletinboardapp.R
import com.github.sahibmobdev.bulletinboardapp.adapters.ImageAdapter
import com.github.sahibmobdev.bulletinboardapp.databinding.ActivityEditAdsBinding
import com.github.sahibmobdev.bulletinboardapp.dialogs.DialogSpinnerHelper
import com.github.sahibmobdev.bulletinboardapp.fragments.FragmentCloseInterface
import com.github.sahibmobdev.bulletinboardapp.fragments.ImageListFragment
import com.github.sahibmobdev.bulletinboardapp.utils.CityHelper
import com.github.sahibmobdev.bulletinboardapp.utils.ImagePicker

class EditAdsAct : AppCompatActivity(), FragmentCloseInterface {

    private var chooseImageFrag : ImageListFragment? = null
    lateinit var binding: ActivityEditAdsBinding
    private val dialog = DialogSpinnerHelper()
    private val imageAdapter: ImageAdapter by lazy { ImageAdapter() }
    var editImagePos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_IMAGES) {
            if (data != null) {

                val returnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)

                if (returnValues?.size!! > 1 && chooseImageFrag == null) {

                    openChooseImageFragment(returnValues)

                } else if (returnValues.size == 1 && chooseImageFrag == null) {

                    imageAdapter.update(returnValues)

                } else if (chooseImageFrag != null) {

                    chooseImageFrag?.updateAdapter(returnValues)

                }
            }
        } else if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE) {
            if (data != null) {
                val uris = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                chooseImageFrag?.setSingleImage(uris?.get(0)!!, editImagePos)
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
        val list = imageAdapter.mainArray
        if (list.size == 0) {
            ImagePicker.getImages(this, 3, ImagePicker.REQUEST_CODE_GET_IMAGES)
        } else {
            openChooseImageFragment(list)
        }
    }

    override fun onFragClose(list: ArrayList<String>) {
        binding.scrollViewMine.visibility = View.VISIBLE
        imageAdapter.update(list)
        chooseImageFrag = null
    }

    private fun openChooseImageFragment(newList: ArrayList<String>) {
        chooseImageFrag = ImageListFragment(this, newList)
        binding.scrollViewMine.visibility = View.GONE
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.place_holder, chooseImageFrag!!).commitAllowingStateLoss()
    }
}