package com.ezike.tobenna.starwarssearch.core.di

import com.ezike.tobenna.starwarssearch.core.BuildConfig
import com.ezike.tobenna.starwarssearch.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.remote.ApiServiceFactory
import com.ezike.tobenna.starwarssearch.remote.remote.CharacterDetailRemoteImpl
import com.ezike.tobenna.starwarssearch.remote.remote.SearchRemoteImpl
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
    val SearchRemoteImpl.bindSearchRemote: SearchRemote

    @get:[Binds Singleton]
    val CharacterDetailRemoteImpl.bindCharacterDetailRemote: CharacterDetailRemote

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
