package com.measify.findtravelnow

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import io.kotzilla.cloudinject.CloudInjectSDK
import io.kotzilla.cloudinject.analytics.koin.analyticsLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.logger.Level
import root.AppInitializer

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

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
        CloudInjectSDK.setup(this)
        AppInitializer.initialize(isDebug = BuildConfig.DEBUG) {
            androidContext(this@MainApplication)
            analyticsLogger(AndroidLogger(Level.INFO))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        CloudInjectSDK.close()
    }
}