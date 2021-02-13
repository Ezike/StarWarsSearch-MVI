package com.ezike.tobenna.starwarssearch.lib_character_search.data.mapper

import com.ezike.tobenna.starwarssearch.lib_character_search.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterEntityMapperTest {

    private val characterEntityMapper = CharacterEntityMapper()

    @Test
    fun `check that mapFromEntity maps data correctly`() {
        val characterEntity: CharacterEntity = DummyData.characterEntity
        val characterDomain: Character = characterEntityMapper.mapFromEntity(characterEntity)
        assertThat(characterEntity.name).isEqualTo(characterDomain.name)
        assertThat(characterEntity.birthYear).isEqualTo(characterDomain.birthYear)
        assertThat(characterEntity.height).isEqualTo(characterDomain.height)
        assertThat(characterEntity.url).isEqualTo(characterDomain.url)
    }

    @Test
    fun `check that mapToEntity maps data correctly`() {
        val character: Character = DummyData.character
        val characterEntity: CharacterEntity = characterEntityMapper.mapToEntity(character)
        assertThat(characterEntity.name).isEqualTo(character.name)
        assertThat(characterEntity.birthYear).isEqualTo(character.birthYear)
        assertThat(characterEntity.height).isEqualTo(character.height)
        assertThat(characterEntity.url).isEqualTo(character.url)
    }
}
