package com.ezike.tobenna.starwarssearch.characterdetail.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface DataModule {

    @get:Binds
    val CharacterDetailRepositoryImpl.characterDetailRepository: CharacterDetailRepository

    companion object {
        @[Provides Singleton]
        fun apiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }
}
