package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.PlanetResponse
import com.ezike.tobenna.starwarssearch.remote.mapper.RemoteModelMapper
import javax.inject.Inject

internal class PlanetRemoteMapper @Inject constructor() :
    RemoteModelMapper<PlanetResponse, PlanetEntity> {

    override fun mapFromModel(model: PlanetResponse): PlanetEntity {
        return PlanetEntity(model.name, model.population)
    }
}
