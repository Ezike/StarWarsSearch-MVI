package com.ezike.tobenna.starwarssearch.lib_character_search.cache.di

import com.ezike.tobenna.starwarssearch.lib_character_search.cache.impl.CharacterDetailCacheImpl
import com.ezike.tobenna.starwarssearch.lib_character_search.cache.impl.SearchHistoryCacheImpl
import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.cache.SearchHistoryCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface CacheModule {

    @get:Binds
    val SearchHistoryCacheImpl.searchHistoryCache: SearchHistoryCache

    @get:Binds
    val CharacterDetailCacheImpl.characterDetailCache: CharacterDetailCache
}
