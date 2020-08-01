package com.ezike.tobenna.starwarssearch.remote.mapper

import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.base.RemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.model.CharacterRemoteModel
import javax.inject.Inject

class CharacterRemoteModelMapper @Inject constructor() :
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
