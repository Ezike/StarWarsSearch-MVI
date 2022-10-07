package com.ezike.tobenna.starwarssearch.characterdetail.mapper

import com.ezike.tobenna.starwarssearch.characterdetail.model.FilmModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Film
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
