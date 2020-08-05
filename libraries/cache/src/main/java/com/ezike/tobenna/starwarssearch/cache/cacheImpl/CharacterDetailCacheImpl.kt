package com.ezike.tobenna.starwarssearch.cache.cacheImpl

import com.ezike.tobenna.starwarssearch.cache.entity.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.cache.mapper.CharacterDetailCacheMapper
import com.ezike.tobenna.starwarssearch.cache.room.CharacterDetailDao
import com.ezike.tobenna.starwarssearch.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import javax.inject.Inject

class CharacterDetailCacheImpl @Inject constructor(
    private val characterDetailDao: CharacterDetailDao,
    private val characterDetailCacheMapper: CharacterDetailCacheMapper
) : CharacterDetailCache {

    override suspend fun saveCharacter(characterDetailEntity: CharacterDetailEntity) {
        val characterModel: CharacterDetailCacheModel =
            characterDetailCacheMapper.mapToModel(characterDetailEntity)
        characterDetailDao.insertCharacter(characterModel)
    }

    override suspend fun fetchCharacter(characterUrl: String): CharacterDetailEntity? {
        val model: CharacterDetailCacheModel? = characterDetailDao.fetchCharacter(characterUrl)
        return model?.let { characterDetailCacheMapper.mapToEntity(model) }
    }
}
