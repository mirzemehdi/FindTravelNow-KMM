package com.measify.findtravelnow

import MainView
import android.graphics.Color
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.permission.permissionUtil
import util.logging.AppLogger

class MainActivity : AppCompatActivity() {

    private val permissionUtil by permissionUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor= Color.TRANSPARENT
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            MainView()
        }
        permissionUtil.askNotificationPermission {
            AppLogger.d("HasNotification Permission: $it")
        }
    }


}