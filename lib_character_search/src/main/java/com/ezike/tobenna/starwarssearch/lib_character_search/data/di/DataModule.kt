package com.ezike.tobenna.starwarssearch.lib_character_search.data.di

import com.ezike.tobenna.starwarssearch.lib_character_search.data.repository.CharacterDetailRepositoryImpl
import com.ezike.tobenna.starwarssearch.lib_character_search.data.repository.SearchHistoryRepositoryImpl
import com.ezike.tobenna.starwarssearch.lib_character_search.data.repository.SearchRepositoryImpl
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.SearchHistoryRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface DataModule {

    @get:Binds
    val SearchRepositoryImpl.searchRepository: SearchRepository

    @get:Binds
    val CharacterDetailRepositoryImpl.characterDetailRepository: CharacterDetailRepository

    @get:Binds
    val SearchHistoryRepositoryImpl.searchHistoryRepository: SearchHistoryRepository
}
