package util.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toLocalDateString() = toLocalDateTime(TimeZone.UTC).date.toString()