package com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.remote.base.RemoteModelMapper
import javax.inject.Inject

class CharacterDetailRemoteMapper @Inject constructor() :
    RemoteModelMapper<CharacterRemoteModel, CharacterDetailEntity> {

    override fun mapFromModel(model: CharacterRemoteModel): CharacterDetailEntity {
        return CharacterDetailEntity(
            model.films,
            model.homeworld,
            model.species,
            model.url
        )
    }
}
