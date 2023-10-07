package root

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object AppInitializer {

    fun initialize(isDebug: Boolean = false, onKoinStart: KoinApplication.() -> Unit) {
        startKoin {
            onKoinStart()
            modules(appModules)
        }
    }
}