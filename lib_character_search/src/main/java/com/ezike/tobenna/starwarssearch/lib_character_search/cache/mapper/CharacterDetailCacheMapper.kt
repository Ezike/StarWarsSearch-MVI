package com.ezike.tobenna.starwarssearch.lib_character_search.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.mapper.CacheModelMapper
import com.ezike.tobenna.starwarssearch.cache.model.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import javax.inject.Inject

internal class CharacterDetailCacheMapper @Inject constructor() :
    CacheModelMapper<CharacterDetailCacheModel, CharacterDetailEntity> {

    override fun mapToModel(entity: CharacterDetailEntity): CharacterDetailCacheModel {
        return CharacterDetailCacheModel(
            entity.filmUrls,
            entity.planetUrl,
            entity.speciesUrls,
            entity.url
        )
    }

    override fun mapToEntity(model: CharacterDetailCacheModel): CharacterDetailEntity {
        return CharacterDetailEntity(
            model.filmUrls,
            model.planetUrl,
            model.speciesUrls,
            model.url
        )
    }
}
