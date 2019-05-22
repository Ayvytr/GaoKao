package com.ayvytr.gaokao.util

import com.ayvytr.network.ApiClient
import okhttp3.Callback
import okhttp3.Request
import ru.noties.markwon.Markwon

/**
 * @author Now
 */

fun Markwon.loadUrl(url: String, callback: Callback) {
    val okHttpClient = ApiClient.getInstance().okHttpClient
    val request = Request.Builder()
        .url(url)
        .build()
    okHttpClient.newCall(request)
        .enqueue(callback)
}

fun String.loadMarkdown(callback:Callback) {
    val okHttpClient = ApiClient.getInstance().okHttpClient
    val request = Request.Builder()
        .url(this)
        .build()
    okHttpClient.newCall(request)
        .enqueue(callback)
}

