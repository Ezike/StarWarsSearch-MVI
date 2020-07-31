package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.domain.model.Character
import javax.inject.Inject

class CharacterEntityMapper @Inject constructor() : EntityMapper<CharacterEntity, Character> {

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
