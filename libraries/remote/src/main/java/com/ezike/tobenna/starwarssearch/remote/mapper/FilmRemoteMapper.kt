package com.ezike.tobenna.starwarssearch.remote.mapper

import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.base.RemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.model.response.FilmResponse
import javax.inject.Inject

class FilmRemoteMapper @Inject constructor() : RemoteModelMapper<FilmResponse, FilmEntity> {

    override fun mapFromModel(model: FilmResponse): FilmEntity {
        return FilmEntity(model.title, model.opening_crawl)
    }
}
