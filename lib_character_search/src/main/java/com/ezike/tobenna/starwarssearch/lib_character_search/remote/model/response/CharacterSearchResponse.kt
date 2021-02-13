package com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.response

import com.ezike.tobenna.starwarssearch.lib_character_search.remote.model.CharacterRemoteModel

internal data class CharacterSearchResponse(
    val results: List<CharacterRemoteModel>,
    val next: String?
)
