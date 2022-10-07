package com.ezike.tobenna.starwarssearch.characterdetail.mapper

import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

class CharacterDetailModelMapper @Inject constructor() :
    ModelMapper<CharacterDetailModel, Character> {

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
