package com.ezike.tobenna.starwarssearch.characterdetail.data

internal data class CharacterDetailEntity(
    val filmUrls: List<String>,
    val planetUrl: String,
    val speciesUrls: List<String>,
    val url: String
)

internal data class CharacterModel(
    val filmUrls: List<String>,
    val planetUrl: String,
    val speciesUrls: List<String>,
    val url: String
)
