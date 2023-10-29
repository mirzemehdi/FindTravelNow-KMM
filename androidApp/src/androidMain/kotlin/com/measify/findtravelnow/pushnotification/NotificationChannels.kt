package com.measify.findtravelnow.pushnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

object NotificationChannels {

    const val ID_GENERAL = "CHANNEL_ID_GENERAL"

    fun createChannels(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(getGeneralChannel(context))
            }
        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun getGeneralChannel(context: Context): NotificationChannel {
        return getNotificationChannel(
            id = ID_GENERAL,
            name = "General",
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(
        id: String,
        name: String,
        description: String = "",
    ): NotificationChannel {
        val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH).apply {
            this.description = description
            enableLights(true)
        }
        return channel
    }


}