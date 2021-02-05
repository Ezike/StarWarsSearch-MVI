package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Film
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFilms @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<List<String>, List<Film>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: List<String>?): Flow<List<Film>> {
        requireParams(params)
        return characterDetailRepository.fetchFilms(params)
    }
}
