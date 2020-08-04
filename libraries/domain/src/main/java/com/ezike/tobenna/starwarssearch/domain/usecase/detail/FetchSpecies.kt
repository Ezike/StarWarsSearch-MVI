package com.ezike.tobenna.starwarssearch.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.model.Specie
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.domain.usecase.base.FlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class FetchSpecies @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<List<String>, List<Specie>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: List<String>?): Flow<List<Specie>> {
        requireParams(params)
        return characterDetailRepository.fetchSpecies(params)
    }
}
