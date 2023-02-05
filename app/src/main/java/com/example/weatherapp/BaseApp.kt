package com.example.weatherapp

import android.app.Application
import com.example.core.util.initFresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp :Application() {
    override fun onCreate() {
        super.onCreate()
        initFresco(this)
    }
}