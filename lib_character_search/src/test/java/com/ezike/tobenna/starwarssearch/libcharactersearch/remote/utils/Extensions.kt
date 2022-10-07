package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.utils

import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.CharacterSearchResponse
import com.google.common.io.Resources
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.lang.reflect.ParameterizedType
import java.net.URL

internal const val REQUEST_PATH: String = "/people/"

private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder().build()

internal val moshi: Moshi
    get() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

internal val adapter: JsonAdapter<CharacterSearchResponse>
    get() {
        val type: ParameterizedType = Types.newParameterizedType(
            CharacterSearchResponse::class.java,
            String::class.java
        )
        return moshi.adapter(type)
    }

@Suppress("UnstableApiUsage")
internal fun getJson(path: String): String {
    val uri: URL = Resources.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}

internal fun makeTestApiService(mockWebServer: MockWebServer): ApiService = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
    .create(ApiService::class.java)
