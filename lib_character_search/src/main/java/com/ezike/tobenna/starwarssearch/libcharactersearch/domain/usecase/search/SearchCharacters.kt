package com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.search

import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.exception.requireParams
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.repository.SearchRepository
import com.ezike.tobenna.starwarssearch.libcharactersearch.domain.usecase.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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
