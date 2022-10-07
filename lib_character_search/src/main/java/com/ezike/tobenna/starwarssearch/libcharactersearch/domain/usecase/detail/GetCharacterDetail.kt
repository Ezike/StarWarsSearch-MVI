package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetail @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<String, CharacterDetail>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: String?): Flow<CharacterDetail> {
        requireParams(params)
        return characterDetailRepository.getCharacterDetail(params)
    }
}
