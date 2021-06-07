package com.ezike.tobenna.starwarssearch.character_detail.mapper

import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

class CharacterDetailModelMapper @Inject constructor() : ModelMapper<CharacterDetailModel, Character> {

    override fun mapToModel(domain: Character): CharacterDetailModel {
        return CharacterDetailModel(
            domain.name,
            domain.birthYear,
            domain.height,
            domain.url
        )
    }

    override fun mapToDomain(model: CharacterDetailModel): Character {
        return Character(
            model.name,
            model.birthYear,
            model.heightCm,
            model.url
        )
    }
}
