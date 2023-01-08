package com.ezike.tobenna.starwarssearch.charactersearch.di

import com.ezike.tobenna.starwarssearch.charactersearch.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.charactersearch.di.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.charactersearch.di.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.charactersearch.di.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.CharacterDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TestModule {

    @Provides
    fun searchHistoryRepository(): SearchHistoryRepository = FakeSearchHistoryRepository()

    @Provides
    fun characterDetailRepository(): CharacterDetailRepository = FakeCharacterDetailRepository()

    @Provides
    fun searchRepository(): SearchRepository = FakeSearchRepository()

    @Provides
    fun executionThread(): PostExecutionThread = TestPostExecutionThread()
}
