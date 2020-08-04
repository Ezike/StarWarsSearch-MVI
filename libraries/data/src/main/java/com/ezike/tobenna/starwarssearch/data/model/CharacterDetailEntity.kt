package com.ezike.tobenna.starwarssearch.data.model

data class CharacterDetailEntity(
    val name: String,
    val birthYear: String,
    val height: String,
    val filmUrls: List<String>,
    val planetUrl: String,
    val speciesUrls: List<String>,
    val url: String
)
