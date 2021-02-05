package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.assertThrows
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.NoParamsException
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.noParamMessage
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.ResponseType
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.CharacterDetail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

class GetCharacterDetailTest {

    private val repository = FakeCharacterDetailRepository()

    private val fetchCharacter = GetCharacterDetail(
        repository,
        TestPostExecutionThread()
    )

    @Test
    fun `check that fetchSpecie returns sp list`() = runBlockingTest {
        repository.characterResponseType = ResponseType.DATA
        val character: CharacterDetail = fetchCharacter(DummyData.characterDetail.url).first()
        assertThat(character).isEqualTo(DummyData.characterDetail)
    }

    @Test
    fun `check that fetchSpecie returns error if call fails`() = runBlockingTest {
        repository.characterResponseType = ResponseType.ERROR
        val exception: SocketTimeoutException = assertThrows {
            fetchCharacter(DummyData.characterDetail.url).collect()
        }
        assertThat(exception).hasMessageThat().isEqualTo(FakeSearchRepository.ERROR_MSG)
    }

    @Test
    fun `check that fetchSpecie throws NoParams exception when called without params`() =
        runBlockingTest {
            val exception: NoParamsException = assertThrows {
                fetchCharacter().collect()
            }
            assertThat(exception).hasMessageThat().isEqualTo(noParamMessage)
        }
}
