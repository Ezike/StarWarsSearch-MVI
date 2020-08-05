package com.ezike.tobenna.starwarssearch.character_search.views.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.FilmViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent.RetryFetchFilm
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.FilmDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.adapter.FilmAdapter
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class FilmsView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<CharacterDetailViewIntent, FilmDetailViewState> {

    @Inject
    lateinit var filmAdapter: FilmAdapter

    private var binding: FilmViewLayoutBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = FilmViewLayoutBinding.inflate(inflater, this, true)
        binding.filmList.adapter = filmAdapter
    }

    override fun render(state: FilmDetailViewState) {
        binding.root.isVisible = true
        when (state) {
            is FilmDetailViewState.Success -> {
                filmAdapter.submitList(state.films)
                binding.emptyView.isVisible = state.films.isEmpty()
                binding.filmLoadingView.root.isVisible = false
                binding.filmErrorState.isVisible = false
            }
            is FilmDetailViewState.Error -> {
                filmAdapter.reset()
                binding.emptyView.isVisible = false
                binding.filmLoadingView.root.isVisible = false
                binding.filmErrorState.isVisible = true
                binding.filmErrorState.setCaption(state.message)
            }
            FilmDetailViewState.Loading -> {
                filmAdapter.reset()
                binding.emptyView.isVisible = false
                binding.filmLoadingView.root.isVisible = true
                binding.filmErrorState.isVisible = false
            }
        }
    }

    fun hide() {
        binding.root.isVisible = false
    }

    fun retryIntent(url: String): Flow<RetryFetchFilm> =
        binding.filmErrorState.clicks.map { RetryFetchFilm(url) }

    override val intents: Flow<CharacterDetailViewIntent>
        get() = emptyFlow()
}
