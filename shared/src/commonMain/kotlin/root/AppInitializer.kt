package root

import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmprevenuecat.purchases.LogLevel
import com.mmk.kmprevenuecat.purchases.Purchases
import di.appModules
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import secrets.BuildConfig
import util.isAndroid
import util.logging.AppLogger

object AppInitializer {

    fun initialize(isDebug: Boolean = false, onKoinStart: KoinApplication.() -> Unit) {
        startKoin {
            onKoinStart()
            modules(appModules)
            onApplicationStart()
        }

        if (isDebug) AppLogger.initialize()

        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(if (isAndroid()) BuildConfig.REVENUECAT_API_KEY_ANDROID else BuildConfig.REVENUECAT_API_KEY_IOS)
    }

    private fun KoinApplication.onApplicationStart() {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                AppLogger.d("FirebaseOnNewToken: $token")
            }
        })
        GoogleAuthProvider.create(GoogleAuthCredentials(serverId = "400988245981-u6ajdq65cv1utc6b0j7mtnhc5ap54kbd.apps.googleusercontent.com"))
    }
}