package com.ezike.tobenna.starwarssearch.remote.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object RemoteModule {

    val provideMoshi: Moshi
        @[Provides Singleton] get() = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
}
