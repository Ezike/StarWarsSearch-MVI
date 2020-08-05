package com.ezike.tobenna.starwarssearch.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.entity.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.cache.mapper.base.CacheModelMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import javax.inject.Inject

class CharacterDetailCacheMapper @Inject constructor() :
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
