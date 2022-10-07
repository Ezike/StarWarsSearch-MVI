package com.ezike.tobenna.starwarssearch.libcharactersearch.data.repository

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.fakes.FakeErrorSearchRemote
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.fakes.FakeSearchRemote
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.mapper.CharacterEntityMapper
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.testutils.assertThrows
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.UnknownHostException

class CharacterRepositoryImplTest {

    private val characterEntityMapper = CharacterEntityMapper()

    private val characterRepository =
        SearchRepositoryImpl(FakeSearchRemote(), characterEntityMapper)

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
            SearchRepositoryImpl(FakeErrorSearchRemote(), characterEntityMapper)
        errorRepository.searchCharacters(DummyData.name).first()
    }

    @Test
    fun `check that searchCharacters returns error message if network call fails`() =
        runBlockingTest {
            val errorRepository =
                SearchRepositoryImpl(FakeErrorSearchRemote(), characterEntityMapper)
            val throwable: UnknownHostException = assertThrows {
                errorRepository.searchCharacters(DummyData.name).first()
            }
            assertThat(throwable).hasMessageThat().isEqualTo(FakeErrorSearchRemote.ERROR)
        }
}
