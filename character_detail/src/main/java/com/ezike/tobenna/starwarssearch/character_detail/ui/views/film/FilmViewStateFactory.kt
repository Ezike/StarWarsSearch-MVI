package com.ezike.tobenna.starwarssearch.character_detail.ui.views.film

import com.ezike.tobenna.starwarssearch.character_search.model.FilmModel

inline fun FilmViewState.state(
    transform: FilmViewStateFactory.() -> FilmViewState
): FilmViewState = transform(FilmViewStateFactory(this))

object FilmViewStateFactory {

    private lateinit var state: FilmViewState

    operator fun invoke(viewState: FilmViewState): FilmViewStateFactory {
        state = viewState
        return this
    }

    val initialState: FilmViewState
        get() = FilmViewState()

    val loading: FilmViewState
        get() = state.copy(
            films = emptyList(),
            errorMessage = null,
            isLoading = true,
            showError = false,
            showFilms = false,
            showEmpty = false,
            showTitle = true
        )

    val hide: FilmViewState
        get() = FilmViewState()

    fun error(message: String): FilmViewState =
        state.copy(
            films = emptyList(),
            errorMessage = message,
            isLoading = false,
            showError = true,
            showFilms = false,
            showEmpty = false,
            showTitle = true
        )

    fun success(films: List<FilmModel>): FilmViewState =
        state.copy(
            films = films,
            errorMessage = null,
            isLoading = false,
            showError = false,
            showTitle = true,
            showFilms = films.isNotEmpty(),
            showEmpty = films.isEmpty()
        )
}
