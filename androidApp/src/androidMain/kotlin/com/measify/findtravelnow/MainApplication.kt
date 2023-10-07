package com.measify.findtravelnow

import android.app.Application
import org.koin.android.ext.koin.androidContext
import root.AppInitializer

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.initialize(isDebug=true) {
            androidContext(this@MainApplication)
        }
    }
}