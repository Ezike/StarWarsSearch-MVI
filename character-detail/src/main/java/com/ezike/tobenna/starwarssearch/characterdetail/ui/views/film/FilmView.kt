package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.film

import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character.detail.databinding.FilmViewLayoutBinding
import com.ezike.tobenna.starwarssearch.characterdetail.ui.adapter.FilmAdapter
import com.ezike.tobenna.starwarssearch.core.ext.init
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class FilmView(
    private val view: FilmViewLayoutBinding,
    characterUrl: String
) : UIComponent<FilmViewState>() {

    private val filmAdapter: FilmAdapter by init { FilmAdapter() }

    init {
        view.filmList.adapter = filmAdapter
        view.filmErrorState.onRetry {
            sendIntent(RetryFetchFilmIntent(characterUrl))
        }
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
