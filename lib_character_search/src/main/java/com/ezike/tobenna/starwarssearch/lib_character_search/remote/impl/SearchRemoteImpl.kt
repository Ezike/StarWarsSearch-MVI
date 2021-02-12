package com.ezike.tobenna.starwarssearch.lib_character_search.remote.impl

import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.ApiService
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper.CharacterRemoteModelMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.CharacterSearchResponse
import javax.inject.Inject

internal class SearchRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterRemoteModelMapper: CharacterRemoteModelMapper
) : SearchRemote {

    override suspend fun searchCharacters(characterName: String): List<CharacterEntity> {
        return buildList {
            val characters: CharacterSearchResponse = apiService.searchCharacters(characterName)
            addAll(characters.results)
            nextPage(characters.next, this::addAll)
        }.map(characterRemoteModelMapper::mapFromModel)
    }

    private suspend fun nextPage(url: String?, result: (List<CharacterRemoteModel>) -> Unit) {
        if (url == null) {
            return
        }
        val nextPage: CharacterSearchResponse = apiService.nextSearchPage(url)
        result(nextPage.results)
        nextPage(nextPage.next, result)
    }
}
