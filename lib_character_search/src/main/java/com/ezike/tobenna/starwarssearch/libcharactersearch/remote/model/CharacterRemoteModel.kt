package com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model

internal data class CharacterRemoteModel(
    val name: String,
    val birth_year: String,
    val height: String,
    val films: List<String>,
    val homeworld: String,
    val species: List<String>,
    val url: String
)
