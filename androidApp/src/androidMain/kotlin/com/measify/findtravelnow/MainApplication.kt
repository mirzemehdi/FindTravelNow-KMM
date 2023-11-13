package com.measify.findtravelnow

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.android.ext.koin.androidContext
import root.AppInitializer

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.initialize(isDebug = BuildConfig.DEBUG) {
            androidContext(this@MainApplication)
        }
        NotifierManager.initialize(
            NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_notification,
                notificationIconColorResId = R.color.orange,
                notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData(
                    id = "CHANNEL_ID_GENERAL",
                    name = "General"
                )
            )
        )
    }
}