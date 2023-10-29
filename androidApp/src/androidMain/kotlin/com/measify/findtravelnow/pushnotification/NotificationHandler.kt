package com.measify.findtravelnow.pushnotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.measify.findtravelnow.MainActivity
import com.measify.findtravelnow.R
import kotlin.random.Random

class NotificationHandler(private val context: Context) {

    fun notify(title: String, body: String) {
        val notificationManager = getNotificationManager()
        val pendingIntent = getPendingIntent()

        val notification = NotificationCompat.Builder(context, NotificationChannels.ID_GENERAL)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setColor(ContextCompat.getColor(context, R.color.orange))
            .build()

        val notificationID = Random.nextInt()
        notificationManager?.notify(notificationID, notification)
    }

    private fun getPendingIntent(deepLink: String = ""): PendingIntent? {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        if (deepLink.isBlank().not()) {
            with(intent) {
                action = Intent.ACTION_VIEW
                data = Uri.parse(deepLink)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }

        return PendingIntent.getActivity(context, 0, intent, flags)
    }

    private fun getNotificationManager(): NotificationManager? {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    }


}