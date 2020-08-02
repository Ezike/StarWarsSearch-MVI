package com.ezike.tobenna.starwarssearch.domain.usecase.searchhistory

import com.ezike.tobenna.starwarssearch.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class ClearSearchHistory @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {

    suspend operator fun invoke() {
        searchHistoryRepository.clearSearchHistory()
    }
}
