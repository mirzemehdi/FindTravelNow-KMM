package com.measify.findtravelnow.pushnotification

import com.google.android.gms.tasks.Task
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

object NotificationUtil {
    suspend fun getMyNotificationToken(): String? {
        return FirebaseMessaging.getInstance().token.asFlow()
            .catch {
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .firstOrNull()
    }

    suspend fun deleteMyNotificationToken() {
        FirebaseMessaging.getInstance().deleteToken()
    }


    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
    }

    fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
private fun <T> Task<T>.asFlow() = callbackFlow<T> {
    this@asFlow
        .addOnSuccessListener { data ->
            if (isClosedForSend) return@addOnSuccessListener
            launch {
                send(data)
            }
        }
        .addOnFailureListener { exception ->
            if (isClosedForSend.not()) close(exception)
        }
    awaitClose { }
}