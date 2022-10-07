package com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Specie
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
