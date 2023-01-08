package com.ezike.tobenna.starwarssearch.charactersearch.data

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

internal interface ApiService {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String): CharacterSearchResponse

    @GET
    suspend fun nextSearchPage(@Url url: String): CharacterSearchResponse
}

internal data class CharacterRemoteModel(
    val name: String,
    val birth_year: String,
    val height: String,
    val films: List<String>,
    val homeworld: String,
    val species: List<String>,
    val url: String
)

internal data class CharacterSearchResponse(
    val results: List<CharacterRemoteModel>,
    val next: String?
)
