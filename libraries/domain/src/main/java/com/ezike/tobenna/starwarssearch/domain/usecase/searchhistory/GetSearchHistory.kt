package com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.SearchHistoryRepository
import com.ezike.tobenna.starwarssearch.domain.usecase.base.FlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetSearchHistory @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
    private val postExecutionThread: PostExecutionThread
) : FlowUseCase<Unit, List<Character>>() {

    override val dispatcher: CoroutineDispatcher
        get() = postExecutionThread.io

    override fun execute(params: Unit?): Flow<List<Character>> {
        return searchHistoryRepository.getSearchHistory()
    }
}
