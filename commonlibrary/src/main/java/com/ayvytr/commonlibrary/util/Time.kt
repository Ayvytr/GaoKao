package com.ayvytr.commonlibrary.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * UTC时间转为本地时间
 * @author Do
 */

fun String.toLocalTime(): String {
    if (isNullOrEmpty()) {
        return ""
    }

    if (this.contains('t', true) && this.endsWith('z', true)) {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        df.setTimeZone(TimeZone.getTimeZone("UTC"))
        val date = df.parse(this)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)
    }

    return this
}