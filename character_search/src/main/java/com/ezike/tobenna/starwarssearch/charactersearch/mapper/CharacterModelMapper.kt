package com.ezike.tobenna.starwarssearch.charactersearch.mapper

import com.ezike.tobenna.starwarssearch.charactersearch.data.CharacterEntity
import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

internal class CharacterModelMapper @Inject constructor() :
    ModelMapper<CharacterModel, CharacterEntity> {

    override fun mapToModel(domain: CharacterEntity): CharacterModel =
        CharacterModel(
            domain.name,
            domain.birthYear,
            domain.height,
            domain.url
        )

    override fun mapToDomain(model: CharacterModel): CharacterEntity =
        CharacterEntity(
            model.name,
            model.birthYear,
            model.heightCm,
            model.url
        )
}
