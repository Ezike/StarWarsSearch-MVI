package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response

import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.CharacterRemoteModel

internal data class CharacterSearchResponse(
    val results: List<CharacterRemoteModel>,
    val next: String?
)
