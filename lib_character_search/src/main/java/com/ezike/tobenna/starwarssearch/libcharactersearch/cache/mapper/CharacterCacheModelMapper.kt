package com.ezike.tobenna.starwarssearch.libcharactersearch.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.mapper.CacheModelMapper
import com.ezike.tobenna.starwarssearch.cache.model.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import javax.inject.Inject

internal class CharacterCacheModelMapper @Inject constructor() :
    CacheModelMapper<CharacterCacheModel, CharacterEntity> {

    @Throws(NumberFormatException::class)
    override fun mapToModel(entity: CharacterEntity): CharacterCacheModel {
        return CharacterCacheModel(
            name = entity.name,
            birthYear = entity.birthYear,
            height = entity.height,
            url = entity.url
        )
    }

    override fun mapToEntity(model: CharacterCacheModel): CharacterEntity {
        return CharacterEntity(
            model.name,
            model.birthYear,
            model.height,
            model.url
        )
    }
}
