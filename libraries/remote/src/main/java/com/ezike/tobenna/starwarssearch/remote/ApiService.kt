package com.ezike.tobenna.starwarssearch.remote

import com.ezike.tobenna.starwarssearch.remote.model.CharacterSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String): CharacterSearchResponse
}
