package com.ezike.tobenna.starwarssearch.cache.mapper

import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.entity.DummyData
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterCacheModelMapperTest {

    private val characterCacheModelMapper = CharacterCacheModelMapper()

    @Test
    fun `check that mapToModel returns correct data`() {
        val entity: CharacterEntity = DummyData.entity
        val model: CharacterCacheModel = characterCacheModelMapper.mapToModel(entity)
        assertThat(entity.name).isEqualTo(model.name)
        assertThat(entity.birthYear).isEqualTo(model.birthYear)
        assertThat(entity.height).isEqualTo(model.height)
        assertThat(entity.url).isEqualTo(model.url)
        assertThat(model.id).isEqualTo(12)
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

    @Test
    fun `check that getCharacterId returns correct Id`() {
        val entity: CharacterEntity = DummyData.entity
        val id: Int = characterCacheModelMapper.getCharacterId(entity.url)
        assertThat(id).isEqualTo(12)
    }

    @Test(expected = NumberFormatException::class)
    fun `check that getCharacterId throws exception when wrong url is passed`() {
        characterCacheModelMapper.getCharacterId("https://swapi.dev/people/1")
    }
}
