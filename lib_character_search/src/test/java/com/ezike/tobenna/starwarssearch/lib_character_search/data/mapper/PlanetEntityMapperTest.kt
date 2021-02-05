package com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.PlanetEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Planet
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PlanetEntityMapperTest {

    private val mapper = PlanetEntityMapper()

    @Test
    fun mapFromEntity() {
        val entity: PlanetEntity = DummyData.planetEntity
        val domain: Planet = mapper.mapFromEntity(entity)
        assertThat(entity.name).isEqualTo(domain.name)
        assertThat(entity.population).isEqualTo(domain.population)
    }

    @Test
    fun mapToEntity() {
        val domain: Planet = DummyData.planet
        val entity: PlanetEntity = mapper.mapToEntity(domain)
        assertThat(domain.name).isEqualTo(entity.name)
        assertThat(domain.population).isEqualTo(entity.population)
    }
}
