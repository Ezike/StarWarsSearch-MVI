package com.ezike.tobenna.starwarssearch.domain.usecase.search

import com.ezike.tobenna.starwarssearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.SearchRepository
import com.ezike.tobenna.starwarssearch.domain.usecase.base.FlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SearchCharacters @Inject constructor(
    private val repository: SearchRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<String, List<Character>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: String?): Flow<List<Character>> {
        requireParams(params)
        return repository.searchCharacters(params)
    }
}
