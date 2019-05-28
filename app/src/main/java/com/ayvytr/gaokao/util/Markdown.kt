package com.ayvytr.gaokao.util

import com.ayvytr.network.ApiClient
import okhttp3.Callback
import okhttp3.Request

/**
 * @author Now
 */


fun String.loadMarkdown(callback:Callback) {
    val okHttpClient = ApiClient.getInstance().okHttpClient
    val request = Request.Builder()
        .url(this)
        .build()
    okHttpClient.newCall(request)
        .enqueue(callback)
}

