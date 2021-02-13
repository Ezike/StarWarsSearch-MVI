package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.assertThrows
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.NoParamsException
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.noParamMessage
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.ResponseType
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

class FetchFilmsTest {

    private val repository = FakeCharacterDetailRepository()

    private val fetchFilms = FetchFilms(
        repository,
        TestPostExecutionThread()
    )

    @Test
    fun `check that fetchFilms returns film list`() = runBlockingTest {
        repository.filmResponseType = ResponseType.DATA
        val films: List<Film> = fetchFilms(DummyData.characterDetail.filmUrls).first()
        assertThat(films.size).isEqualTo(1)
        assertThat(films.first()).isEqualTo(DummyData.film)
    }

    @Test
    fun `check that fetchFilms returns empty`() = runBlockingTest {
        repository.filmResponseType = ResponseType.EMPTY
        fetchFilms(DummyData.characterDetail.filmUrls).collect { films ->
            assertThat(films).isEmpty()
        }
    }

    @Test
    fun `check that fetchFilms returns error if call fails`() = runBlockingTest {
        repository.filmResponseType = ResponseType.ERROR
        val exception: SocketTimeoutException = assertThrows {
            fetchFilms(DummyData.characterDetail.filmUrls).collect()
        }
        assertThat(exception).hasMessageThat().isEqualTo(FakeSearchRepository.ERROR_MSG)
    }

    @Test
    fun `check that fetchFilms throws NoParams exception when called without params`() =
        runBlockingTest {
            val exception: NoParamsException = assertThrows {
                fetchFilms().collect()
            }
            assertThat(exception).hasMessageThat().isEqualTo(noParamMessage)
        }
}
