package com.ezike.tobenna.starwarssearch.core.di

import com.ezike.tobenna.starwarssearch.data.repository.CharacterRepositoryImpl
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @get:[Binds Singleton]
    val CharacterRepositoryImpl.characterRepository: CharacterRepository
}
