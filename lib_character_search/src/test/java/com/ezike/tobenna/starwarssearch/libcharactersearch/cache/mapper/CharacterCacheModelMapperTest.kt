package com.ezike.tobenna.starwarssearch.libcharactersearch.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.model.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.cache.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterCacheModelMapperTest {

    private val characterCacheModelMapper =
        CharacterCacheModelMapper()

    @Test
    fun `check that mapToModel returns correct data`() {
        val entity: CharacterEntity = DummyData.entity
        val model: CharacterCacheModel = characterCacheModelMapper.mapToModel(entity)
        assertThat(entity.name).isEqualTo(model.name)
        assertThat(entity.birthYear).isEqualTo(model.birthYear)
        assertThat(entity.height).isEqualTo(model.height)
        assertThat(entity.url).isEqualTo(model.url)
    }

    @Test
    fun `check that mapToEntity returns correct data`() {
        val model: CharacterCacheModel = DummyData.characterModel
        val entity: CharacterEntity = characterCacheModelMapper.mapToEntity(model)
        assertThat(model.name).isEqualTo(entity.name)
        assertThat(model.birthYear).isEqualTo(entity.birthYear)
        assertThat(model.height).isEqualTo(entity.height)
        assertThat(model.url).isEqualTo(entity.url)
    }
}
