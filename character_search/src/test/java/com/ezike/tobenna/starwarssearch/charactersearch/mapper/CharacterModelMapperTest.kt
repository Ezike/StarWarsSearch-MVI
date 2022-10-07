package com.ezike.tobenna.starwarssearch.charactersearch.mapper

import com.ezike.tobenna.starwarssearch.charactersearch.data.DummyData
import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterModelMapperTest {

    private val characterModelMapper = CharacterModelMapper()

    @Test
    fun `check that mapToModel returns correct data`() {
        val character: Character = DummyData.character
        val model: CharacterModel = characterModelMapper.mapToModel(character)
        assertThat(character.name).isEqualTo(model.name)
        assertThat(character.birthYear).isEqualTo(model.birthYear)
        assertThat(character.height).isEqualTo(model.heightCm)
        assertThat(character.url).isEqualTo(model.url)
    }

    @Test
    fun `check that mapToDomain returns correct data`() {
        val model: CharacterModel = DummyData.characterModel
        val characterDomain: Character = characterModelMapper.mapToDomain(model)
        assertThat(model.name).isEqualTo(characterDomain.name)
        assertThat(model.birthYear).isEqualTo(characterDomain.birthYear)
        assertThat(model.heightCm).isEqualTo(characterDomain.height)
        assertThat(model.url).isEqualTo(characterDomain.url)
    }
}
