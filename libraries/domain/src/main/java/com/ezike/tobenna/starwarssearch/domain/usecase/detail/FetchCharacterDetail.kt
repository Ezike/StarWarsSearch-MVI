package com.ezike.tobenna.starwarssearch.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.domain.usecase.base.FlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class FetchCharacterDetail @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<String, CharacterDetail>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: String?): Flow<CharacterDetail> {
        requireParams(params)
        return characterDetailRepository.fetchCharacter(params)
    }
}
