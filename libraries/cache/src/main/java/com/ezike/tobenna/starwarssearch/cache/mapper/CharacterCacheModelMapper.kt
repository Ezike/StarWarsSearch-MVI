package com.ezike.tobenna.starwarssearch.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.mapper.base.CacheModelMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import javax.inject.Inject

class CharacterCacheModelMapper @Inject constructor() :
    CacheModelMapper<CharacterCacheModel, CharacterEntity> {

    /**
     * When using this mapper, the operation should be wrapped in a try/catch so that if
     * [getCharacterId] throws a [NumberFormatException], the operation should be aborted
     */
    @Throws(NumberFormatException::class)
    override fun mapToModel(entity: CharacterEntity): CharacterCacheModel {
        return CharacterCacheModel(
            id = getCharacterId(entity.url),
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

    @Throws(NumberFormatException::class)
    fun getCharacterId(url: String): Int = url.substringBeforeLast("/")
        .substringAfterLast("/").toInt()
}
