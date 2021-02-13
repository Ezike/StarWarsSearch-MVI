package com.ezike.tobenna.starwarssearch.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KClass

internal object NoInternetInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val serverResponse: Response
        try {
            serverResponse = chain.proceed(chain.request())
        } catch (e: Exception) {
            throw noNetworkException(e)
        }

        return serverResponse
    }

    private fun noNetworkException(exception: Throwable): Throwable {
        val networkExceptions: List<KClass<out IOException>> =
            listOf(
                SocketTimeoutException::class,
                ConnectException::class,
                UnknownHostException::class
            )
        return if (exception::class in networkExceptions) {
            IOException("Please check your internet connection and retry")
        } else exception
    }
}
