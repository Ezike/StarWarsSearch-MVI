package com.ezike.tobenna.starwarssearch.libcharactersearch.remote

import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.CharacterRemoteModel
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.CharacterSearchResponse
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.FilmResponse
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.PlanetResponse
import com.ezike.tobenna.starwarssearch.libcharactersearch.remote.model.response.SpecieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

internal interface ApiService {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String): CharacterSearchResponse

    @GET
    suspend fun nextSearchPage(@Url url: String): CharacterSearchResponse

    @GET
    suspend fun fetchCharacterDetail(@Url url: String): CharacterRemoteModel

    @GET
    suspend fun fetchSpecieDetails(@Url speciesUrl: String): SpecieResponse

    @GET
    suspend fun fetchFilmDetails(@Url filmsUrl: String): FilmResponse

    @GET
    suspend fun fetchPlanet(@Url characterUrl: String): PlanetResponse
}
