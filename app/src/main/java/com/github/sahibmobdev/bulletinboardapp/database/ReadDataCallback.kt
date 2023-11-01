package com.github.sahibmobdev.bulletinboardapp.database

import com.github.sahibmobdev.bulletinboardapp.data.Advert

interface ReadDataCallback {
    fun readData(list: List<Advert>)
}