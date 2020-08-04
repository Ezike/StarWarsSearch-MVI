package com.ezike.tobenna.starwarssearch.remote.mapper

import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.base.RemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.model.CharacterRemoteModel
import javax.inject.Inject

class CharacterDetailRemoteMapper @Inject constructor() : RemoteModelMapper<CharacterRemoteModel, CharacterDetailEntity> {

    override fun mapFromModel(model: CharacterRemoteModel): CharacterDetailEntity {
        return CharacterDetailEntity(
            model.name,
            model.birth_year,
            model.height,
            model.films,
            model.homeworld,
            model.species,
            model.url
        )
    }
}
