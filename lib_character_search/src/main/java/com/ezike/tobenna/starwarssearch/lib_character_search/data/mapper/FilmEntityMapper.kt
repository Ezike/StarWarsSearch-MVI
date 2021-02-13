package com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper.base.EntityMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.FilmEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
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
