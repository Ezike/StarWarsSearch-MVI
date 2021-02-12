package com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.SpecieResponse
import com.ezike.tobenna.starwarssearch.remote.base.RemoteModelMapper
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
