package com.ayvytr.commonlibrary.util

import android.net.Uri
import com.ayvytr.logger.L
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ru.noties.markwon.image.ImageItem
import ru.noties.markwon.image.SchemeHandler
import java.io.IOException

class OkHttpSchemeHandler(private val client: OkHttpClient, private val baseUrl: String) : SchemeHandler() {

    override fun handle(raw: String, uri: Uri): ImageItem? {
        var url = raw
        L.e(url, uri)
        if (!url.isUrl() && !baseUrl.isNullOrEmpty()) {
            url = baseUrl.substringBeforeLast('/') + "/" + raw
            L.e(url)
        }
        var out: ImageItem? = null

        val request = Request.Builder()
            .url(url)
            .tag(url)
            .build()

        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (response != null) {
            val body = response.body()
            if (body != null) {
                val inputStream = body.byteStream()
                if (inputStream != null) {
                    val contentType = response.header(HEADER_CONTENT_TYPE)
                    out = ImageItem(contentType, inputStream)
                }
            }
        }

        return out
    }

    companion object {

        private val HEADER_CONTENT_TYPE = "Content-Type"
    }
}
