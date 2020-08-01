package com.ezike.tobenna.starwarssearch.core.di

import androidx.viewbinding.BuildConfig
import com.ezike.tobenna.starwarssearch.data.contract.CharacterRemote
import com.ezike.tobenna.starwarssearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.remote.ApiServiceFactory
import com.ezike.tobenna.starwarssearch.remote.remote.CharacterRemoteImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RemoteModule {

    @get:[Binds Singleton]
    val CharacterRemoteImpl.bindCharacterRemote: CharacterRemote

    companion object {
        @get:[Provides Singleton]
        val provideMoshi: Moshi
            get() = Moshi.Builder()
                .add(KotlinJsonAdapterFactory()).build()

        @[Provides Singleton]
        fun provideApiService(moshi: Moshi): ApiService =
            ApiServiceFactory.createApiService(BuildConfig.DEBUG, moshi)
    }
}
