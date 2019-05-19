package com.ayvytr.commonlibrary.util

/**
 * @author Now
 */

fun String.isUrl(): Boolean {
    return this.startsWith("http://") || this.startsWith("https://")
}