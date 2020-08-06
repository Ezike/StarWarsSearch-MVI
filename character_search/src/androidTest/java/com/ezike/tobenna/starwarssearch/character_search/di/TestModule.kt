package com.ezike.tobenna.starwarssearch.character_search.di

import com.ezike.tobenna.starwarssearch.character_search.di.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.character_search.di.fakes.FakeSearchHistoryRepository
import com.ezike.tobenna.starwarssearch.character_search.di.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.domain.repository.SearchHistoryRepository
import com.ezike.tobenna.starwarssearch.domain.repository.SearchRepository
import com.ezike.tobenna.starwarssearch.testutils.TestPostExecutionThread
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
