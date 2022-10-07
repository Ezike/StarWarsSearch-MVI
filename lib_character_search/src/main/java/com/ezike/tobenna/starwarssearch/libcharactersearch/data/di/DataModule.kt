package com.ezike.tobenna.starwarssearch.libcharactersearch.data.di

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.repository.CharacterDetailRepositoryImpl
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.repository.SearchHistoryRepositoryImpl
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.repository.SearchRepositoryImpl
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.SearchHistoryRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.SearchRepository
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
