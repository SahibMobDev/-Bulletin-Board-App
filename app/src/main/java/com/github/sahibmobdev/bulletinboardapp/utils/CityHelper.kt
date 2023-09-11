package com.github.sahibmobdev.bulletinboardapp.utils

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object CityHelper {
    fun getAllCountries(context: Context) : ArrayList<String> {
        var tempArray = ArrayList<String>()
        try {

            val inputStream : InputStream = context.assets.open("countriesToCities.json")
            val size: Int = inputStream.available()
            val bytesArray = ByteArray(size)
            inputStream.read(bytesArray)
            val jsonFile = String(bytesArray)
            val jsonObject = JSONObject(jsonFile)
            val countriesNames = jsonObject.names()
            if (countriesNames != null) {
                for (n in 0 until countriesNames.length()) {
                    tempArray.add(countriesNames.getString(n))
                }
            }

        }catch (e: IOException) {

        }
        return tempArray
    }

    fun getAllCities(country: String, context: Context): ArrayList<String> {
        var tempArray = ArrayList<String>()
        try {

            val inputStream = context.assets.open("countriesToCities.json")
            val size = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)

            val jsonFile = String(byteArray)
            val jsonObject = JSONObject(jsonFile)
            val citiesNames = jsonObject.getJSONArray(country)

            for (n in 0 until citiesNames.length()) {
                tempArray.add(citiesNames.getString(n))
            }
        } catch (e: IOException) {

        }
        return tempArray
    }

    fun filterListData(list: ArrayList<String>, searchText: String?): ArrayList<String> {
        val tempList = ArrayList<String>()
        tempList.clear()
        if (searchText == null) {
            tempList.add("No result")
            return tempList
        }
        for (selection in list) {
            if (selection.lowercase().startsWith(searchText.lowercase()))
                tempList.add(selection)
        }
        if (tempList.size == 0) {
            tempList.add("No result")
        }
        return tempList
    }
}