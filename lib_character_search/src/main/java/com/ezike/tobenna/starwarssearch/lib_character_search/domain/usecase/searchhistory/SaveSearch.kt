package com.ezike.tobenna.starwarssearch.lib_character_search.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.lib_character_search.domain.executor.PostExecutionThread
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.model.Character
import com.ezike.tobenna.starwarssearch.lib_character_search.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
