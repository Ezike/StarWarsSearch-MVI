package com.ezike.tobenna.starwarssearch.data.mapper

import com.ezike.tobenna.starwarssearch.data.DummyData
import com.ezike.tobenna.starwarssearch.data.model.CharacterDetailEntity
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterDetailEntityMapperTest {

    private val mapper = CharacterDetailEntityMapper()
    @Test
    fun mapFromEntity() {
        val entity: CharacterDetailEntity = DummyData.characterDetailEntity
        val domain: CharacterDetail = mapper.mapFromEntity(entity)
        assertThat(entity.filmUrls).isEqualTo(domain.filmUrls)
        assertThat(entity.planetUrl).isEqualTo(domain.planetUrl)
        assertThat(entity.speciesUrls).isEqualTo(domain.speciesUrls)
        assertThat(entity.url).isEqualTo(domain.url)
    }

    @Test
    fun mapToEntity() {
        val domain: CharacterDetail = DummyData.characterDetail
        val entity: CharacterDetailEntity = mapper.mapToEntity(domain)
        assertThat(domain.filmUrls).isEqualTo(entity.filmUrls)
        assertThat(domain.planetUrl).isEqualTo(entity.planetUrl)
        assertThat(domain.speciesUrls).isEqualTo(entity.speciesUrls)
        assertThat(domain.url).isEqualTo(entity.url)
    }
}
