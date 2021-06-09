package com.ezike.tobenna.starwarssearch.lib_character_search.remote

import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.impl.SearchRemoteImpl
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper.CharacterRemoteModelMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.CharacterSearchResponse
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.NO_MATCH_SEARCH_QUERY
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.REQUEST_PATH
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.RequestDispatcher
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.SEARCH_QUERY
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.SEARCH_RESPONSE_PATH
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.SEARCH_RESPONSE_PATH_2
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.adapter
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.getJson
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var searchRemote: SearchRemote
    private val characterRemoteModelMapper = CharacterRemoteModelMapper()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = RequestDispatcher()
        mockWebServer.start()
        searchRemote =
            SearchRemoteImpl(makeTestApiService(mockWebServer), characterRemoteModelMapper)
    }

    @Test
    fun `check that searchCharacters returns character list of same size`() = runBlocking {
        val characters: List<CharacterEntity> = searchRemote.searchCharacters(SEARCH_QUERY)
        val responseSize: Int =
            getResponseList(SEARCH_RESPONSE_PATH, SEARCH_RESPONSE_PATH_2).sumOf {
                it.results.size
            }
        assertThat(characters).isNotEmpty()
        assertThat(characters.size).isEqualTo(responseSize)
    }

    @Test
    fun `check that searchCharacters returns correct data`() {
        runBlocking {
            val apiResponse: List<CharacterRemoteModel> =
                getResponseList(SEARCH_RESPONSE_PATH, SEARCH_RESPONSE_PATH_2).flatMap { it.results }
            val mappedResponse: List<CharacterEntity> =
                characterRemoteModelMapper.mapModelList(apiResponse)
            val characterEntity: List<CharacterEntity> = searchRemote.searchCharacters(SEARCH_QUERY)
            assertThat(mappedResponse).containsExactlyElementsIn(characterEntity)
        }
    }

    @Test
    fun `check that calling searchCharacters makes request to correct path`() = runBlocking {
        searchRemote.searchCharacters(SEARCH_QUERY)
        assertThat("$REQUEST_PATH?search=$SEARCH_QUERY")
            .isEqualTo(mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling searchCharacters makes a GET request`() = runBlocking {
        searchRemote.searchCharacters(SEARCH_QUERY)
        assertThat("GET $REQUEST_PATH?search=$SEARCH_QUERY HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @Test
    fun `check that searchCharacters returns empty list when no character is found`() =
        runBlocking {
            val characters: List<CharacterEntity> =
                searchRemote.searchCharacters(NO_MATCH_SEARCH_QUERY)
            assertThat(characters).isEmpty()
        }

    private fun getResponse(responsePath: String): CharacterSearchResponse {
        return adapter.fromJson(getJson(responsePath))!!
    }

    private fun getResponseList(vararg responsePath: String): List<CharacterSearchResponse> {
        return responsePath.map(::getResponse)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
