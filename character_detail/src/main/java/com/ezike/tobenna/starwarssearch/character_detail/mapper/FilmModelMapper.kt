package com.ezike.tobenna.starwarssearch.character_detail.mapper

import com.ezike.tobenna.starwarssearch.character_search.model.FilmModel
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

class FilmModelMapper @Inject constructor() : ModelMapper<FilmModel, Film> {

    override fun mapToModel(domain: Film): FilmModel {
        return FilmModel(domain.title, domain.openingCrawl)
    }

    override fun mapToDomain(model: FilmModel): Film {
        return Film(model.title, model.openingCrawl)
    }
}
