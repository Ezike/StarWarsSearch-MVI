package com.ezike.tobenna.starwarssearch.lib_character_search.remote.remote

import com.ezike.tobenna.starwarssearch.lib_character_search.data.contract.remote.SearchRemote
import com.ezike.tobenna.starwarssearch.lib_character_search.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.ApiService
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.mapper.CharacterRemoteModelMapper
import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response.CharacterSearchResponse
import javax.inject.Inject

class SearchRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterRemoteModelMapper: CharacterRemoteModelMapper
) : SearchRemote {

    override suspend fun searchCharacters(characterName: String): List<CharacterEntity> {
        val characters: CharacterSearchResponse = apiService.searchCharacters(characterName)
        return characterRemoteModelMapper.mapModelList(characters.results)
    }
}
