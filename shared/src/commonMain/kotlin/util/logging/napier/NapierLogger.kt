package util.logging.napier

import io.github.aakira.napier.Napier
import util.logging.AppLogger
import util.logging.Logger

class NapierLogger : Logger {
    override fun initialize() {
        Napier.base(
            DebugAntilog(
                excludedClassList = listOf(
                    NapierLogger::class.qualifiedName ?: "",
                    AppLogger::class.qualifiedName ?: ""
                )
            )
        )
    }

    override fun e(message: String?) {
        Napier.e(message ?: "")
    }

    override fun d(message: String?) {
        Napier.d(message ?: "")
    }

    override fun i(message: String?) {
        Napier.i(message ?: "")
    }


}