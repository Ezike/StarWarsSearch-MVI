package com.ezike.tobenna.starwarssearch.character_search.ui.views.result

import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel

inline fun SearchResultViewState.state(
    transform: SearchResultViewStateFactory.() -> SearchResultViewState
): SearchResultViewState = transform(SearchResultViewStateFactory(this))

object SearchResultViewStateFactory {

    private lateinit var state: SearchResultViewState

    operator fun invoke(
        viewState: SearchResultViewState
    ): SearchResultViewStateFactory {
        state = viewState
        return this
    }

    val initialState: SearchResultViewState
        get() = SearchResultViewState()

    val Hide: SearchResultViewState
        get() = SearchResultViewState()

    val Searching: SearchResultViewState
        get() = state.copy(
            showProgress = state.searchResult.isEmpty(),
            showEmpty = false,
            showResult = state.searchResult.isNotEmpty(),
            showError = false,
            error = null
        )

    fun Error(
        message: String
    ): SearchResultViewState = state.copy(
        searchResult = emptyList(),
        showProgress = false,
        showEmpty = false,
        showResult = false,
        showError = true,
        error = message
    )

    fun ResultLoaded(
        characters: List<CharacterModel>
    ) = state.copy(
        searchResult = characters,
        showProgress = false,
        showEmpty = characters.isEmpty(),
        showResult = characters.isNotEmpty(),
        showError = false,
        error = null
    )
}
