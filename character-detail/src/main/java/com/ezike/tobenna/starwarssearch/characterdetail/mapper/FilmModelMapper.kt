package com.ezike.tobenna.starwarssearch.characterdetail.mapper

import com.ezike.tobenna.starwarssearch.characterdetail.data.FilmEntity
import com.ezike.tobenna.starwarssearch.characterdetail.model.FilmModel
import com.ezike.tobenna.starwarssearch.presentation.mapper.ModelMapper
import javax.inject.Inject

internal class FilmModelMapper @Inject constructor() : ModelMapper<FilmModel, FilmEntity> {

    override fun mapToModel(domain: FilmEntity): FilmModel =
        FilmModel(domain.title, domain.opening_crawl)

    override fun mapToDomain(model: FilmModel): FilmEntity =
        FilmEntity(model.title, model.openingCrawl)
}
