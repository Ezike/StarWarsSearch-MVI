package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.DummyData
import com.ezike.tobenna.starwarssearch.data.model.SpecieEntity
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SpeciesEntityMapperTest {

    private val mapper = SpeciesEntityMapper()

    @Test
    fun mapFromEntity() {
        val entity: SpecieEntity = DummyData.specieEntity
        val domain: Specie = mapper.mapFromEntity(entity)
        assertThat(entity.homeWorld).isEqualTo(domain.homeWorld)
        assertThat(entity.language).isEqualTo(domain.language)
        assertThat(entity.name).isEqualTo(domain.name)
    }

    @Test
    fun mapToEntity() {
        val domain: Specie = DummyData.specie
        val entity: SpecieEntity = mapper.mapToEntity(domain)
        assertThat(domain.homeWorld).isEqualTo(entity.homeWorld)
        assertThat(domain.language).isEqualTo(entity.language)
        assertThat(domain.name).isEqualTo(entity.name)
    }
}
