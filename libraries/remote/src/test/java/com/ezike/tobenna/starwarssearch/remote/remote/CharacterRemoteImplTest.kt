package com.ezike.tobenna.starwarssearch.remote.remote

import com.ezike.tobenna.starwarssearch.data.contract.CharacterRemote
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.remote.mapper.CharacterRemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.utils.REQUEST_PATH
import com.ezike.tobenna.starwarssearch.remote.utils.RequestDispatcher
import com.ezike.tobenna.starwarssearch.remote.utils.SEARCH_QUERY
import com.ezike.tobenna.starwarssearch.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharacterRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var characterRemote: CharacterRemote
    private val characterRemoteModelMapper = CharacterRemoteModelMapper()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = RequestDispatcher()
        mockWebServer.start()
        characterRemote =
            CharacterRemoteImpl(makeTestApiService(mockWebServer), characterRemoteModelMapper)
    }

    @Test
    fun `check that searchCharacters returns character list`() = runBlocking {
        val characters: List<CharacterEntity> = characterRemote.searchCharacters(SEARCH_QUERY)
        assertThat(characters).isNotEmpty()
        assertThat(characters.size).isEqualTo(2)
    }

    @Test
    fun `check that searchCharacters returns correct data`() = runBlocking {
        val characters: List<CharacterEntity> = characterRemote.searchCharacters(SEARCH_QUERY)
        val firstCharacter: CharacterEntity = characters.first()
        val lastCharacter: CharacterEntity = characters.last()
        assertThat(firstCharacter.name).isEqualTo("Anakin Skywalker")
        assertThat(firstCharacter.birthYear).isEqualTo("41.9BBY")
        assertThat(firstCharacter.height).isEqualTo("188")
        assertThat(lastCharacter.name).isEqualTo("Quarsh Panaka")
        assertThat(lastCharacter.birthYear).isEqualTo("62BBY")
        assertThat(lastCharacter.height).isEqualTo("183")
    }

    @Test
    fun `check that calling searchCharacters makes request to correct path`() = runBlocking {
        characterRemote.searchCharacters(SEARCH_QUERY)
        assertThat("$REQUEST_PATH?search=$SEARCH_QUERY")
            .isEqualTo(mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling searchCharacters makes a GET request`() = runBlocking {
        characterRemote.searchCharacters(SEARCH_QUERY)
        assertThat("GET $REQUEST_PATH?search=$SEARCH_QUERY HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
