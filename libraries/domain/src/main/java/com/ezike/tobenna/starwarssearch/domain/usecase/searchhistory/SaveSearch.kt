package com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.model.Character
import com.ezike.tobenna.starwarssearch.domain.repository.SearchHistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

class SaveSearch @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
    private val postExecutionThread: PostExecutionThread
) {

    suspend operator fun invoke(character: Character) {
        withContext(postExecutionThread.io) {
            searchHistoryRepository.saveSearch(character)
        }
    }
}
