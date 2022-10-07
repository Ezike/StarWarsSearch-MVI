package com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Film
import javax.inject.Inject

internal class FilmEntityMapper @Inject constructor() : EntityMapper<FilmEntity, Film> {

    override fun mapFromEntity(entity: FilmEntity): Film {
        return Film(
            entity.title,
            entity.openingCrawl
        )
    }

    override fun mapToEntity(domain: Film): FilmEntity {
        return FilmEntity(
            domain.title,
            domain.openingCrawl
        )
    }
}
