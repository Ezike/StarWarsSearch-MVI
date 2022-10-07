package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.CharacterRemoteModel
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
