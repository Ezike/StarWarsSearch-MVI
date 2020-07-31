package com.ezike.tobenna.starwarssearch.remote.model

import com.squareup.moshi.Json

data class CharacterRemoteModel(
    val name: String,
    @field:Json(name = "birth_year") val birthYear: String,
    val height: String,
    val url: String
)
