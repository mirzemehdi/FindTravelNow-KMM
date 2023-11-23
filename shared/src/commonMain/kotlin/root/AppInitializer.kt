package root

import com.mmk.kmpnotifier.notification.NotifierManager
import di.appModules
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import util.logging.AppLogger

object AppInitializer {

    fun initialize(isDebug: Boolean = false, onKoinStart: KoinApplication.() -> Unit) {
        startKoin {
            onKoinStart()
            modules(appModules)
            onApplicationStart()
        }

        if (isDebug) AppLogger.initialize()
    }

    private fun KoinApplication.onApplicationStart() {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                AppLogger.d("FirebaseOnNewToken: $token")
            }
        })
    }
}