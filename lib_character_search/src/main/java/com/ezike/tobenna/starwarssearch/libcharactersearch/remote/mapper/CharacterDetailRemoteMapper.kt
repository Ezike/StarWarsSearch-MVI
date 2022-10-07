package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.remote.mapper.RemoteModelMapper
import javax.inject.Inject

internal class CharacterDetailRemoteMapper @Inject constructor() :
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
