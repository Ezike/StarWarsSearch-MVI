package com.ezike.tobenna.starwarssearch.remote

import com.ezike.tobenna.starwarssearch.remote.interceptor.HttpsInterceptor
import com.ezike.tobenna.starwarssearch.remote.interceptor.NoInternetInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

public class RemoteFactory @Inject constructor(private val moshi: Moshi) {

    public fun createRetrofit(url: String, isDebug: Boolean): Retrofit {
        val client: OkHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return Retrofit.Builder()
            .baseUrl(url)
            .delegatingCallFactory { client }
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpsInterceptor)
            .addInterceptor(NoInternetInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun Retrofit.Builder.delegatingCallFactory(
        delegate: dagger.Lazy<OkHttpClient>
    ): Retrofit.Builder = callFactory {
        delegate.get().newCall(it)
    }

    private inline fun Retrofit.Builder.callFactory(
        crossinline body: (Request) -> Call
    ): Retrofit.Builder = callFactory(
        object : Call.Factory {
            override fun newCall(request: Request): Call = body(request)
        }
    )
}
