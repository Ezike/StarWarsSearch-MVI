package com.ezike.tobenna.starwarssearch.libcharactersearch.data.fakes

import com.ezike.tobenna.starwarssearch.libcharactersearch.data.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.libcharactersearch.data.model.CharacterEntity
import java.net.UnknownHostException

internal class FakeSearchRemote : SearchRemote {

    override suspend fun searchCharacters(characterName: String): List<CharacterEntity> {
        return listOf(DummyData.characterEntity)
    }
}

internal class FakeErrorSearchRemote : SearchRemote {

    override suspend fun searchCharacters(characterName: String): List<CharacterEntity> {
        throw UnknownHostException(ERROR)
    }

    companion object {
        const val ERROR: String = "Cannot resolve host `https://swapi.dev`"
    }
}
