package com.ezike.tobenna.starwarssearch.core.di

import com.ezike.tobenna.starwarssearch.core.BuildConfig
import com.ezike.tobenna.starwarssearch.core.executor.PostExecutionThreadImpl
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.remote.ApiServiceFactory
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ExecutorModule {

    @get:[Binds Singleton]
    val PostExecutionThreadImpl.postExecutionThread: PostExecutionThread

    companion object {
        @[Provides Singleton]
        fun provideApiService(moshi: Moshi): ApiService =
            ApiServiceFactory.createApiService(BuildConfig.DEBUG, moshi)
    }
}
