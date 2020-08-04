package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.domain.model.Film
import javax.inject.Inject

class FilmEntityMapper @Inject constructor() : EntityMapper<FilmEntity, Film> {

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
