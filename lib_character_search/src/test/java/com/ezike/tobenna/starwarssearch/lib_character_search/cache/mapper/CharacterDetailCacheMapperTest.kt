package com.ezike.tobenna.starwarssearch.lib_character_search.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.entity.CharacterDetailCacheModel
import com.ezike.tobenna.starwarssearch.lib_character_search.cache.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterDetailEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterDetailCacheMapperTest {

    private val mapper = CharacterDetailCacheMapper()

    @Test
    fun mapToModel() {
        val entity: CharacterDetailEntity = DummyData.characterDetailEntity
        val model: CharacterDetailCacheModel = mapper.mapToModel(entity)
        assertThat(entity.url).isEqualTo(model.url)
        assertThat(entity.filmUrls).isEqualTo(model.filmUrls)
        assertThat(entity.planetUrl).isEqualTo(model.planetUrl)
        assertThat(entity.speciesUrls).isEqualTo(model.speciesUrls)
    }

    @Test
    fun mapToEntity() {
        val model: CharacterDetailCacheModel = DummyData.characterDetailModel
        val entity: CharacterDetailEntity = mapper.mapToEntity(model)
        assertThat(model.filmUrls).isEqualTo(entity.filmUrls)
        assertThat(model.planetUrl).isEqualTo(entity.planetUrl)
        assertThat(model.speciesUrls).isEqualTo(entity.speciesUrls)
    }
}
