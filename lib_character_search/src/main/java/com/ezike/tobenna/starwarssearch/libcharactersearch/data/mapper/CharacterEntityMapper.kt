package com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import javax.inject.Inject

internal class CharacterEntityMapper @Inject constructor() : EntityMapper<CharacterEntity, Character> {

    override fun mapFromEntity(entity: CharacterEntity): Character {
        return Character(
            entity.name,
            entity.birthYear,
            entity.height,
            entity.url
        )
    }

    override fun mapToEntity(domain: Character): CharacterEntity {
        return CharacterEntity(
            domain.name,
            domain.birthYear,
            domain.height,
            domain.url
        )
    }
}
