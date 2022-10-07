package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.mapper

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.CharacterRemoteModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CharacterRemoteModelMapperTest {

    private val characterRemoteModelMapper = CharacterRemoteModelMapper()

    @Test
    fun `check that mapFromModel returns correct data`() {
        val characterRemoteModel: CharacterRemoteModel = DummyData.characterRemoteModel
        val characterEntity: CharacterEntity =
            characterRemoteModelMapper.mapFromModel(characterRemoteModel)
        assertThat(characterRemoteModel.name).isEqualTo(characterEntity.name)
        assertThat(characterRemoteModel.birth_year).isEqualTo(characterEntity.birthYear)
        assertThat(characterRemoteModel.height).isEqualTo(characterEntity.height)
        assertThat(characterRemoteModel.url).isEqualTo(characterEntity.url)
    }
}
