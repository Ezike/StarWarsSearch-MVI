package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.detail

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.CharacterDetail
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.CharacterDetailRepository
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.base.FlowUseCase
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
