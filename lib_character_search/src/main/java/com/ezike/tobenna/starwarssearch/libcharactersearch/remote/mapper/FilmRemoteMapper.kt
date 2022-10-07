package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.FilmResponse
import com.ezike.tobenna.starwarssearch.remote.mapper.RemoteModelMapper
import javax.inject.Inject

internal class FilmRemoteMapper @Inject constructor() : RemoteModelMapper<FilmResponse, FilmEntity> {

    override fun mapFromModel(model: FilmResponse): FilmEntity {
        return FilmEntity(model.title, model.opening_crawl)
    }
}
