package com.ezike.tobenna.starwarssearch.remote.mapper

import com.ezike.tobenna.starwarssearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.base.RemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.model.response.PlanetResponse
import javax.inject.Inject

class PlanetRemoteMapper @Inject constructor() :
    RemoteModelMapper<PlanetResponse, PlanetEntity> {

    override fun mapFromModel(model: PlanetResponse): PlanetEntity {
        return PlanetEntity(model.name, model.population)
    }
}
