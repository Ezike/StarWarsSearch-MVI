package com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.PlanetResponse
import com.ezike.tobenna.starwarssearch.remote.base.RemoteModelMapper
import javax.inject.Inject

internal class PlanetRemoteMapper @Inject constructor() :
    RemoteModelMapper<PlanetResponse, PlanetEntity> {

    override fun mapFromModel(model: PlanetResponse): PlanetEntity {
        return PlanetEntity(model.name, model.population)
    }
}
