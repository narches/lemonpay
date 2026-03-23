

package com.example.lemon.utils


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Instant.toReadableDate(): String {
    val formatter = DateTimeFormatter
        .ofPattern("dd MMM yyyy, HH:mm")
        .withZone(ZoneId.systemDefault())

    return formatter.format(this)
}

