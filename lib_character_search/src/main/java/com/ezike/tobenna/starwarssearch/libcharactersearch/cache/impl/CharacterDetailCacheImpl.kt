package com.ezike.tobenna.starwarssearch.libcharactersearch.cache.impl

import com.ezike.tobenna.starwarssearch.cache.model.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.cache.room.CharacterDetailDao
import com.ezike.tobenna.starwarssearch.libcharactersearch.cache.mapper.CharacterDetailCacheMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.cache.CharacterDetailCache
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterDetailEntity
import javax.inject.Inject

internal class CharacterDetailCacheImpl @Inject constructor(
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
