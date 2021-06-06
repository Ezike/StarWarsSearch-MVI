package com.ezike.tobenna.starwarssearch.character_search.mapper

import com.ezike.tobenna.starwarssearch.character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Planet
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PlanetModelMapperTest {

    private val mapper = PlanetModelMapper()

    @Test
    fun mapToModel() {
        val planet: Planet = DummyData.planet
        val model: PlanetModel = mapper.mapToModel(planet)
        assertThat(planet.name).isEqualTo(model.name)
        assertThat(planet.population).isEqualTo(model.population)
    }

    @Test
    fun mapToDomain() {
        val model: PlanetModel = DummyData.planetModel
        val planet: Planet = mapper.mapToDomain(model)
        assertThat(model.name).isEqualTo(planet.name)
        assertThat(model.population).isEqualTo(planet.population)
    }
}
