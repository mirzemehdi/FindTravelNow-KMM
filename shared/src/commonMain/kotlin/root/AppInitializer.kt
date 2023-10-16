package root

import di.appModules
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import util.logging.AppLogger

object AppInitializer {

    fun initialize(isDebug: Boolean = false, onKoinStart: KoinApplication.() -> Unit) {
        startKoin {
            onKoinStart()
            modules(appModules)
        }
        if (isDebug) AppLogger.initialize()
    }
}