package com.ezike.tobenna.starwarssearch.remote.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object HttpsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()

        if (!request.url.isHttps) {
            val newUrl: HttpUrl = request.url.newBuilder()
                .scheme("https")
                .host(request.url.host)
                .build()
            requestBuilder.url(newUrl)
        }
        val newRequest: Request = requestBuilder.build()
        val response: Response?

        try {
            response = chain.proceed(newRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return response
    }
}
