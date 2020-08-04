package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import javax.inject.Inject

class SpeciesEntityMapper @Inject constructor() : EntityMapper<SpecieEntity, Specie> {

    override fun mapFromEntity(entity: SpecieEntity): Specie {
        return Specie(
            entity.name,
            entity.language
        )
    }

    override fun mapToEntity(domain: Specie): SpecieEntity {
        return SpecieEntity(
            domain.name,
            domain.language
        )
    }
}
