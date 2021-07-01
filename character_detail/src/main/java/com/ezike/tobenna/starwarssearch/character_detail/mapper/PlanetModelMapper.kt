package com.ezike.tobenna.starwarssearch.character_detail.mapper

import com.ezike.tobenna.starwarssearch.character_detail.model.PlanetModel
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Planet
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

class PlanetModelMapper @Inject constructor() : ModelMapper<PlanetModel, Planet> {

    override fun mapToModel(domain: Planet): PlanetModel {
        return PlanetModel(domain.name, domain.population)
    }

    override fun mapToDomain(model: PlanetModel): Planet {
        return Planet(model.name, model.population)
    }
}
