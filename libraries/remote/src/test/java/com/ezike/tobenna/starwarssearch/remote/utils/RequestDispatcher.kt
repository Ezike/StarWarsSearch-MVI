package com.ezike.tobenna.starwarssearch.remote.utils

import java.net.HttpURLConnection
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class RequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "$REQUEST_PATH?search=$SEARCH_QUERY" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("response/character_search.json"))
            }
            "$REQUEST_PATH/?search=$NO_MATCH_SEARCH_QUERY" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(
                        getJson("response/character_search_no_match.json")
                    )
            }
            NO_MATCH_CHARACTER_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                    .setBody(getJson("response/not_found.json"))
            }
            CHARACTER_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("response/character_details.json"))
            }
            SPECIES_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("response/characters_species.json"))
            }
            FILM_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("response/character_films.json"))
            }
            PLANET_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("response/character_planet.json"))
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
        }
    }
}
