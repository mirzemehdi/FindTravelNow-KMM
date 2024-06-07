package util.extensions

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime


fun Instant.toLocalDateString() = toLocalDateTime(TimeZone.UTC).date.toString()

fun String.toFormattedDate(format: String): String {
    val localDateTime = LocalDate.parse(this)
    val day = localDateTime.dayOfMonth
    val month = localDateTime.monthNumber
    val year = localDateTime.year
    return when (format) {
        "ddMM" -> "${day.zeroPrefixed(2)}${month.zeroPrefixed(2)}"
        else -> this
    }

}

//Credits to @gosr
private fun Int.zeroPrefixed(maxLength: Int): String {
    if (this < 0 || maxLength < 1) return ""
    val string = this.toString()
    val currentStringLength = string.length
    return if (maxLength <= currentStringLength) {
        string
    } else {
        val diff = maxLength - currentStringLength
        var prefixedZeros = ""
        repeat(diff) {
            prefixedZeros += "0"
        }
        "$prefixedZeros$string"
    }
}