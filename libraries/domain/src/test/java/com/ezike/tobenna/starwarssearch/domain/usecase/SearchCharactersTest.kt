package com.ezike.tobenna.starwarssearch.domain.usecase

import com.ezike.tobenna.starwarssearch.domain.assertThrows
import com.ezike.tobenna.starwarssearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.domain.exception.NoParamsException
import com.ezike.tobenna.starwarssearch.domain.exception.noParamMessage
import com.ezike.tobenna.starwarssearch.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeSearchRepository.Companion.ERROR_MSG
import com.ezike.tobenna.starwarssearch.domain.fakes.ResponseType
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.usecase.search.SearchCharacters
import com.google.common.truth.Truth.assertThat
import java.net.SocketTimeoutException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchCharactersTest {

    private val fakeCharacterRepository = FakeSearchRepository()

    private val searchCharacters =
        SearchCharacters(fakeCharacterRepository, TestPostExecutionThread())

    @Test
    fun `check that calling searchCharacters returns list of characters`() = runBlockingTest {
        fakeCharacterRepository.responseType = ResponseType.DATA
        val characters: List<Character> = searchCharacters(DummyData.name).first()
        assertThat(characters.size).isAtLeast(1)
    }

    @Test
    fun `check that calling searchCharacters returns correct data`() = runBlockingTest {
        fakeCharacterRepository.responseType = ResponseType.DATA
        val characters: List<Character> = searchCharacters(DummyData.name).first()
        val character: Character = characters.first()
        assertThat(character.name).isEqualTo(DummyData.character.name)
        assertThat(character.birthYear).isEqualTo(DummyData.character.birthYear)
        assertThat(character.height).isEqualTo(DummyData.character.height)
        assertThat(character.url).isEqualTo(DummyData.character.url)
    }

    @Test
    fun `check that calling searchCharacters returns empty list if response is empty`() =
        runBlockingTest {
            fakeCharacterRepository.responseType = ResponseType.EMPTY
            val characters: List<Character> = searchCharacters(DummyData.name).first()
            assertThat(characters).isEmpty()
        }

    @Test
    fun `check that calling searchCharacters returns error if call fails`() = runBlockingTest {
        fakeCharacterRepository.responseType = ResponseType.ERROR
        val exception: SocketTimeoutException = assertThrows {
            searchCharacters(DummyData.name).collect()
        }
        assertThat(exception).hasMessageThat().isEqualTo(ERROR_MSG)
    }

    @Test
    fun `check that calling searchCharacters without id throws NoParamException`() =
        runBlockingTest {
            val exception: NoParamsException = assertThrows {
                searchCharacters().collect()
            }
            assertThat(exception).hasMessageThat().isEqualTo(noParamMessage)
        }
}
