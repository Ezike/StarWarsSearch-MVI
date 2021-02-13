package com.ezike.tobenna.starwarssearch.character_search.mapper

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
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
