package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Film
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.base.FlowUseCase
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
