package util.logging.napier

import io.github.aakira.napier.Antilog

/**
 * This class is created for adding capability excluding
 * facade class name from stackTrace for tagging
 */
expect class DebugAntilog(
    defaultTag: String = "Logger",
    excludedClassList: List<String> = emptyList(),
) : Antilog