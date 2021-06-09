package com.ezike.tobenna.starwarssearch.character_detail.views

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_detail.databinding.FilmViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_detail.ui.adapter.FilmAdapter
import com.ezike.tobenna.starwarssearch.character_search.model.FilmModel
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class RetryFetchFilmIntent(val url: String) : ViewIntent

class FilmView(
    private val view: FilmViewLayoutBinding,
    characterUrl: String
) : UIComponent<FilmViewState>() {

    private val filmAdapter: FilmAdapter by init { FilmAdapter() }

    init {
        view.filmList.adapter = filmAdapter
        view.filmErrorState.onRetry { sendIntent(RetryFetchFilmIntent(characterUrl)) }
    }

    override fun render(state: FilmViewState) {
        filmAdapter.submitList(state.films)
        view.run {
            filmTitle.isVisible = state.showTitle
            emptyView.isVisible = state.showEmpty
            filmLoadingView.root.isVisible = state.isLoading
            filmErrorState.isVisible = state.showError
            filmErrorState.setCaption(state.errorMessage)
        }
    }
}

data class FilmViewState private constructor(
    val films: List<FilmModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val showFilms: Boolean = false,
    val showTitle: Boolean = false,
    val showEmpty: Boolean = false
) : ViewState {

    inline fun state(transform: Factory.() -> FilmViewState): FilmViewState =
        transform(Factory(this))

    companion object Factory {

        private lateinit var state: FilmViewState

        operator fun invoke(viewState: FilmViewState): Factory {
            state = viewState
            return this
        }

        val init: FilmViewState
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
}
