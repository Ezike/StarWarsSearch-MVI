package com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.remote.mapper.RemoteModelMapper
import javax.inject.Inject

internal class CharacterRemoteModelMapper @Inject constructor() :
    RemoteModelMapper<CharacterRemoteModel, CharacterEntity> {

    override fun mapFromModel(model: CharacterRemoteModel): CharacterEntity {
        return CharacterEntity(
            model.name,
            model.birth_year,
            model.height,
            model.url
        )
    }
}
