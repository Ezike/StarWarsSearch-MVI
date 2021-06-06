package com.ezike.tobenna.starwarssearch.character_search.mapper

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FilmModelMapperTest {

    private val mapper = FilmModelMapper()

    @Test
    fun mapToModel() {
        val film: Film = DummyData.film
        val model: FilmModel = mapper.mapToModel(film)
        assertThat(film.openingCrawl).isEqualTo(model.openingCrawl)
        assertThat(film.title).isEqualTo(model.title)
    }

    @Test
    fun mapToModelList() {
        val films: List<Film> = DummyData.films
        val model: List<FilmModel> = mapper.mapToModelList(films)
        assertThat(model).isNotEmpty()
        assertThat(films[0].openingCrawl).isEqualTo(model[0].openingCrawl)
        assertThat(films[0].title).isEqualTo(model[0].title)
    }

    @Test
    fun mapToDomainList() {
        val model: List<FilmModel> = listOf(DummyData.filmModel)
        val films: List<Film> = mapper.mapToDomainList(model)
        assertThat(films).isNotEmpty()
        assertThat(model[0].openingCrawl).isEqualTo(films[0].openingCrawl)
        assertThat(model[0].title).isEqualTo(films[0].title)
    }

    @Test
    fun mapToDomain() {
        val model: FilmModel = DummyData.filmModel
        val film: Film = mapper.mapToDomain(model)
        assertThat(model.openingCrawl).isEqualTo(film.openingCrawl)
        assertThat(model.title).isEqualTo(film.title)
    }
}
