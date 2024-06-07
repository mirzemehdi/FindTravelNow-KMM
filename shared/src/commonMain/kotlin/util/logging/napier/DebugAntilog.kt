package util.logging.napier

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

/**
 * This class is created for adding capability excluding
 * facade class name from stackTrace for auto tagging
 */
expect class DebugAntilog(
    defaultTag: String = "Logger",
    excludedClassList: List<String> = emptyList(),
) : Antilog{
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    )
}