package com.example.data.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun String.toTime(): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(this)
            .withZoneSameInstant(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "--:--"
    }
}

fun String.toDate(): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(this)
            .withZoneSameInstant(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "00/00/0000"
    }
}