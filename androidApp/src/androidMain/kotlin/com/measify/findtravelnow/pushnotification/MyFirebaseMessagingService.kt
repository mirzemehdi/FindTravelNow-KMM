package com.measify.findtravelnow.pushnotification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.*
import util.logging.AppLogger

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val notificationHandler: NotificationHandler by lazy { NotificationHandler(this.applicationContext) }
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //TODO Update token
        AppLogger.d("New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            notificationHandler.notify(it.title ?: "", it.body ?: "")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}