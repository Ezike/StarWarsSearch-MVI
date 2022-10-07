package com.ezike.tobenna.starwarssearch.charactersearch.mapper

import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

class CharacterModelMapper @Inject constructor() : ModelMapper<CharacterModel, Character> {

    override fun mapToModel(domain: Character): CharacterModel {
        return CharacterModel(
            domain.name,
            domain.birthYear,
            domain.height,
            domain.url
        )
    }

    override fun mapToDomain(model: CharacterModel): Character {
        return Character(
            model.name,
            model.birthYear,
            model.heightCm,
            model.url
        )
    }
}
