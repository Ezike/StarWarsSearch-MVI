package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.assertThrows
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.NoParamsException
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.noParamMessage
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes.ResponseType
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Planet
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

class FetchPlanetTest {

    private val repository = FakeCharacterDetailRepository()

    private val fetchPlanet = FetchPlanet(
        repository,
        TestPostExecutionThread()
    )

    @Test
    fun `check that fetchPlanet returns planet list`() = runBlockingTest {
        repository.planetResponseType = ResponseType.DATA
        val planet: Planet = fetchPlanet(DummyData.characterDetail.planetUrl).first()
        assertThat(planet).isEqualTo(DummyData.planet)
    }

    @Test
    fun `check that fetchPlanet returns error if call fails`() = runBlockingTest {
        repository.planetResponseType = ResponseType.ERROR
        val exception: SocketTimeoutException = assertThrows {
            fetchPlanet(DummyData.characterDetail.planetUrl).collect()
        }
        assertThat(exception).hasMessageThat().isEqualTo(FakeSearchRepository.ERROR_MSG)
    }

    @Test
    fun `check that fetchPlanet throws NoParams exception when called without params`() =
        runBlockingTest {
            val exception: NoParamsException = assertThrows {
                fetchPlanet().collect()
            }
            assertThat(exception).hasMessageThat().isEqualTo(noParamMessage)
        }
}
