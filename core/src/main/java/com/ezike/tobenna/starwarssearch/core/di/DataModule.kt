package com.ezike.tobenna.starwarssearch.core.di

import com.ezike.tobenna.starwarssearch.data.repository.CharacterRepositoryImpl
import com.ezike.tobenna.starwarssearch.data.repository.SearchHistoryRepositoryImpl
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterRepository
import com.ezike.tobenna.starwarssearch.domain.repository.SearchHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @get:Binds
    val CharacterRepositoryImpl.characterRepository: CharacterRepository

    @get:Binds
    val SearchHistoryRepositoryImpl.searchHistoryRepository: SearchHistoryRepository
}
