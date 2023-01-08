package com.ezike.tobenna.starwarssearch.remote.di

import com.ezike.tobenna.starwarssearch.remote.RemoteFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteModule {

    val provideMoshi: Moshi
        @[Provides Singleton] get() = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideRetrofit(
        remoteFactory: RemoteFactory
    ): Retrofit = remoteFactory.retrofit
}
