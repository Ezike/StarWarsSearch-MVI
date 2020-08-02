package com.ezike.tobenna.starwarssearch.data.repository

import com.ezike.tobenna.starwarssearch.data.DummyData
import com.ezike.tobenna.starwarssearch.data.fakes.FakeCharacterRemote
import com.ezike.tobenna.starwarssearch.data.fakes.FakeErrorCharacterRemote
import com.ezike.tobenna.starwarssearch.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.testutils.assertThrows
import com.google.common.truth.Truth.assertThat
import java.net.UnknownHostException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CharacterRepositoryImplTest {

    private val characterEntityMapper = CharacterEntityMapper()

    private val characterRepository =
        CharacterRepositoryImpl(FakeCharacterRemote(), characterEntityMapper)

    @Test
    fun `check that searchCharacters returns data`() = runBlockingTest {
        val characters: List<Character> =
            characterRepository.searchCharacters(DummyData.name).first()
        assertThat(characters).isNotEmpty()
    }

    @Test
    fun `check that searchCharacters returns correct data`() = runBlockingTest {
        val character: Character =
            characterRepository.searchCharacters(DummyData.name).first().first()
        assertThat(character.name).isEqualTo(DummyData.character.name)
        assertThat(character.birthYear).isEqualTo(DummyData.character.birthYear)
        assertThat(character.height).isEqualTo(DummyData.character.height)
        assertThat(character.url).isEqualTo(DummyData.character.url)
    }

    @Test(expected = UnknownHostException::class)
    fun `check that searchCharacters throws error if network call fails`() = runBlockingTest {
        val errorRepository =
            CharacterRepositoryImpl(FakeErrorCharacterRemote(), characterEntityMapper)
        errorRepository.searchCharacters(DummyData.name).first()
    }

    @Test
    fun `check that searchCharacters returns error message if network call fails`() =
        runBlockingTest {
            val errorRepository =
                CharacterRepositoryImpl(FakeErrorCharacterRemote(), characterEntityMapper)
            val throwable: UnknownHostException = assertThrows {
                errorRepository.searchCharacters(DummyData.name).first()
            }
            assertThat(throwable).hasMessageThat().isEqualTo(FakeErrorCharacterRemote.ERROR)
        }
}
