package com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Specie
import javax.inject.Inject

internal class SpeciesEntityMapper @Inject constructor() : EntityMapper<SpecieEntity, Specie> {

    override fun mapFromEntity(entity: SpecieEntity): Specie {
        return Specie(
            entity.name,
            entity.language,
            entity.homeWorld
        )
    }

    override fun mapToEntity(domain: Specie): SpecieEntity {
        return SpecieEntity(
            domain.name,
            domain.language,
            domain.homeWorld
        )
    }
}
