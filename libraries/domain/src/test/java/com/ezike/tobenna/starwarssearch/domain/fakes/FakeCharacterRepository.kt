package com.ezike.tobenna.starwarssearch.domain.fakes

import com.ezike.tobenna.starwarssearch.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterRepository
import java.net.SocketTimeoutException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

internal class FakeCharacterRepository : CharacterRepository {

    companion object {
        const val ERROR_MSG: String = "No network"
    }

    private var charactersFlow: Flow<List<Character>> = flowOf(listOf(DummyData.character))

    var responseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            charactersFlow = makeResponse(value)
        }

    private fun makeResponse(type: ResponseType): Flow<List<Character>> {
        return when (type) {
            ResponseType.DATA -> flowOf(listOf(DummyData.character))
            ResponseType.EMPTY -> flowOf(listOf())
            ResponseType.ERROR -> flow { throw SocketTimeoutException(ERROR_MSG) }
        }
    }

    override fun searchCharacters(characterName: String): Flow<List<Character>> {
        return charactersFlow
    }
}

internal enum class ResponseType {
    DATA,
    EMPTY,
    ERROR
}
