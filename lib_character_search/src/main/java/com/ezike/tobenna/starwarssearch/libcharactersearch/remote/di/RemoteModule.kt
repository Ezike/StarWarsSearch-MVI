package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.di

import com.ezike.tobenna.starwarssearch.libcharactersearch.BuildConfig
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.remote.CharacterDetailRemote
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.impl.CharacterDetailRemoteImpl
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.impl.SearchRemoteImpl
import com.ezike.tobenna.starwarssearch.remote.RemoteFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface RemoteModule {

    @get:Binds
    val SearchRemoteImpl.bindSearchRemote: SearchRemote

    @get:Binds
    val CharacterDetailRemoteImpl.bindCharacterDetailRemote: CharacterDetailRemote

    companion object {
        @[Provides Singleton]
        fun apiService(remoteFactory: RemoteFactory): ApiService =
            remoteFactory.createRetrofit(
                url = BuildConfig.BASE_URL,
                isDebug = BuildConfig.DEBUG
            ).create(ApiService::class.java)
    }
}
