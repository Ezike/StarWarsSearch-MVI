package com.ezike.tobenna.starwarssearch.characterdetail.mapper

import com.ezike.tobenna.starwarssearch.characterdetail.data.PlanetEntity
import com.ezike.tobenna.starwarssearch.characterdetail.model.PlanetModel
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

internal class PlanetModelMapper @Inject constructor() : ModelMapper<PlanetModel, PlanetEntity> {

    override fun mapToModel(domain: PlanetEntity): PlanetModel =
        PlanetModel(domain.name, domain.population)

    override fun mapToDomain(model: PlanetModel): PlanetEntity =
        PlanetEntity(model.name, model.population)
}
