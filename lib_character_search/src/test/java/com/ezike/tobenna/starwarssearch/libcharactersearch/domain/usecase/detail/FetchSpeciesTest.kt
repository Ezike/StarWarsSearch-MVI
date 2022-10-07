package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.assertThrows
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.exception.NoParamsException
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.exception.noParamMessage
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes.FakeCharacterDetailRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes.FakeSearchRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.fakes.ResponseType
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Specie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.net.SocketTimeoutException

class FetchSpeciesTest {

    private val repository = FakeCharacterDetailRepository()

    private val fetchSpecie = FetchSpecies(
        repository,
        TestPostExecutionThread()
    )

    @Test
    fun `check that fetchSpecie returns species list`() = runBlockingTest {
        repository.specieResponseType = ResponseType.DATA
        val species: List<Specie> = fetchSpecie(DummyData.characterDetail.speciesUrls).first()
        assertThat(species).isNotEmpty()
        assertThat(species.first()).isEqualTo(DummyData.specie)
    }

    @Test
    fun `check that fetchSpecie returns empty`() = runBlockingTest {
        repository.specieResponseType = ResponseType.EMPTY
        fetchSpecie(DummyData.characterDetail.speciesUrls).collect { species ->
            assertThat(species).isEmpty()
        }
    }

    @Test
    fun `check that fetchSpecie returns error if call fails`() = runBlockingTest {
        repository.specieResponseType = ResponseType.ERROR
        val exception: SocketTimeoutException = assertThrows {
            fetchSpecie(DummyData.characterDetail.speciesUrls).collect()
        }
        assertThat(exception).hasMessageThat().isEqualTo(FakeSearchRepository.ERROR_MSG)
    }

    @Test
    fun `check that fetchSpecie throws NoParams exception when called without params`() =
        runBlockingTest {
            val exception: NoParamsException = assertThrows {
                fetchSpecie().collect()
            }
            assertThat(exception).hasMessageThat().isEqualTo(noParamMessage)
        }
}
