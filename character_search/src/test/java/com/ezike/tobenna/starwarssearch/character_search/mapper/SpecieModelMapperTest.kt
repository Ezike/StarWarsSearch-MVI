package com.ezike.tobenna.starwarssearch.character_search.mapper

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SpecieModelMapperTest {

    private val mapper = SpecieModelMapper()

    @Test
    fun mapToModel() {
        val specie: Specie = DummyData.specie
        val model: SpecieModel = mapper.mapToModel(specie)
        assertThat(specie.name).isEqualTo(model.name)
        assertThat(specie.language).isEqualTo(model.language)
        assertThat(specie.homeWorld).isEqualTo(model.homeWorld)
    }

    @Test
    fun mapToModelList() {
        val species: List<Specie> = DummyData.species
        val model: List<SpecieModel> = mapper.mapToModelList(species)
        assertThat(model).isNotEmpty()
        assertThat(species[0].name).isEqualTo(model[0].name)
        assertThat(species[0].language).isEqualTo(model[0].language)
        assertThat(species[0].homeWorld).isEqualTo(model[0].homeWorld)
    }

    @Test
    fun mapToDomain() {
        val specie: SpecieModel = DummyData.specieModel
        val domain: Specie = mapper.mapToDomain(specie)
        assertThat(specie.name).isEqualTo(domain.name)
        assertThat(specie.language).isEqualTo(domain.language)
        assertThat(specie.homeWorld).isEqualTo(domain.homeWorld)
    }

    @Test
    fun mapToDomainList() {
        val species: List<SpecieModel> = listOf(DummyData.specieModel)
        val domain: List<Specie> = mapper.mapToDomainList(species)
        assertThat(domain).isNotEmpty()
        assertThat(species[0].name).isEqualTo(domain[0].name)
        assertThat(species[0].language).isEqualTo(domain[0].language)
        assertThat(species[0].homeWorld).isEqualTo(domain[0].homeWorld)
    }
}
