package com.ezike.tobenna.starwarssearch.lib_character_search.domain.fakes

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.data.DummyData
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

internal class FakeSearchRepository : SearchRepository {

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
