package com.ezike.tobenna.starwarssearch.charactersearch.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
internal interface DataModule {

    @get:Binds
    val SearchRepositoryImpl.searchRepository: SearchRepository

    companion object {
        @Provides
        fun apiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }
}
