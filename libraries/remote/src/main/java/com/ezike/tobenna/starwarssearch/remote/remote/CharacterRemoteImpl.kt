package com.ezike.tobenna.starwarssearch.remote.remote

import com.ezike.tobenna.starwarssearch.data.contract.CharacterRemote
import com.ezike.tobenna.starwarssearch.data.model.CharacterEntity
import com.ezike.tobenna.starwarssearch.remote.ApiService
import com.ezike.tobenna.starwarssearch.remote.mapper.CharacterRemoteModelMapper
import com.ezike.tobenna.starwarssearch.remote.model.response.CharacterSearchResponse
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.reflect.KClass

class CharacterRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterRemoteModelMapper: CharacterRemoteModelMapper
) : CharacterRemote {

    override suspend fun searchCharacters(characterName: String): List<CharacterEntity> {
        try {
            val characters: CharacterSearchResponse = apiService.searchCharacters(characterName)
            return characterRemoteModelMapper.mapModelList(characters.results)
        } catch (e: Exception) {
            throw checkException(e)
        }
    }
}

fun checkException(exception: Throwable): Throwable {
    val networkExceptions: List<KClass<out IOException>> =
        listOf(SocketTimeoutException::class, ConnectException::class, UnknownHostException::class)
    return if (exception::class in networkExceptions) {
        Throwable("Please check your internet connection and retry")
    } else exception
}
