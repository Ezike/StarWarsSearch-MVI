package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.SpecieResponse
import com.ezike.tobenna.starwarssearch.remote.mapper.RemoteModelMapper
import javax.inject.Inject

internal class SpecieRemoteMapper @Inject constructor() :
    RemoteModelMapper<SpecieResponse, SpecieEntity> {

    override fun mapFromModel(model: SpecieResponse): SpecieEntity {
        return SpecieEntity(
            model.name,
            model.language,
            ""
        )
    }
}
