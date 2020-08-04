package com.ezike.tobenna.starwarssearch.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.domain.usecase.base.FlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

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
