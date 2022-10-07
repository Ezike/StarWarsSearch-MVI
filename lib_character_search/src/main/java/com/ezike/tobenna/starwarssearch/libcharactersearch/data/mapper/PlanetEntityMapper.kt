package com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Planet
import javax.inject.Inject

internal class PlanetEntityMapper @Inject constructor() : EntityMapper<PlanetEntity, Planet> {

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
