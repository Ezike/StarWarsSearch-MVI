package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.domain.model.Planet
import javax.inject.Inject

class PlanetEntityMapper @Inject constructor() : EntityMapper<PlanetEntity, Planet> {

    override fun mapFromEntity(entity: PlanetEntity): Planet {
        return Planet(
            entity.name,
            entity.population
        )
    }

    override fun mapToEntity(domain: Planet): PlanetEntity {
        return PlanetEntity(
            domain.name,
            domain.population
        )
    }
}
