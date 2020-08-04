package com.ezike.tobenna.starwarssearch.remote.model.response

import com.ezike.tobenna.starwarssearch.remote.model.CharacterRemoteModel

data class CharacterSearchResponse(
    val results: List<CharacterRemoteModel>
)
