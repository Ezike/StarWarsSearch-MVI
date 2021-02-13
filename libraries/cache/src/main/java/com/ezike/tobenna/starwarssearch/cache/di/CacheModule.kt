package com.ezike.tobenna.starwarssearch.cache.di

import android.content.Context
import com.ezike.tobenna.starwarssearch.cache.room.CharacterDetailDao
import com.ezike.tobenna.starwarssearch.cache.room.SearchHistoryDao
import com.ezike.tobenna.starwarssearch.cache.room.StarWarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object CacheModule {
    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): StarWarsDatabase {
        return StarWarsDatabase.build(context)
    }

    @[Provides Singleton]
    fun provideSearchHistoryDao(starWarsDatabase: StarWarsDatabase): SearchHistoryDao {
        return starWarsDatabase.searchHistoryDao
    }

    @[Provides Singleton]
    fun provideCharacterDetailDao(starWarsDatabase: StarWarsDatabase): CharacterDetailDao {
        return starWarsDatabase.characterDetailDao
    }
}
