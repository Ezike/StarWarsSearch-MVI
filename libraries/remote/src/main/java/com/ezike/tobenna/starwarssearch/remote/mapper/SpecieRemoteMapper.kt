package com.ezike.tobenna.starwarssearch.remote.mapper

import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.base.RemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.model.response.SpecieResponse
import javax.inject.Inject

class SpecieRemoteMapper @Inject constructor() :
    RemoteModelMapper<SpecieResponse, SpecieEntity> {

    override fun mapFromModel(model: SpecieResponse): SpecieEntity {
        return SpecieEntity(
            model.name,
            model.language,
            ""
        )
    }
}
