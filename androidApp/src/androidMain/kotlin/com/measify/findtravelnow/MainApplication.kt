package com.measify.findtravelnow

import android.app.Application
import com.measify.findtravelnow.pushnotification.NotificationChannels
import org.koin.android.ext.koin.androidContext
import root.AppInitializer

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.initialize(isDebug=BuildConfig.DEBUG) {
            androidContext(this@MainApplication)
        }
        NotificationChannels.createChannels(this)
    }
}