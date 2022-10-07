package com.ezike.tobenna.starwarssearch.charactersearch.di.fakes

import com.ezike.tobenna.starwarssearch.charactersearch.ui.DummyData
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.SearchRepository
import com.ezike.tobenna.starwarssearch.testutils.ERROR_MSG
import com.ezike.tobenna.starwarssearch.testutils.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.net.SocketTimeoutException

class FakeSearchRepository : SearchRepository {

    private var charactersFlow: Flow<List<Character>> = flowOf(DummyData.characterList)

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
