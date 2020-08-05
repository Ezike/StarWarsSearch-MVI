package com.ezike.tobenna.starwarssearch.character_search.views.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.SpecieViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent.RetryFetchSpecie
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.adapter.SpecieAdapter
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SpecieView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<CharacterDetailViewIntent, SpecieDetailViewState> {

    @Inject
    lateinit var specieAdapter: SpecieAdapter

    private var binding: SpecieViewLayoutBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SpecieViewLayoutBinding.inflate(inflater, this, true)
        binding.specieList.adapter = specieAdapter
    }

    override fun render(state: SpecieDetailViewState) {
        binding.root.isVisible = true
        when (state) {
            is SpecieDetailViewState.Success -> {
                specieAdapter.submitList(state.species)
                binding.emptyView.isVisible = state.species.isEmpty()
                binding.specieLoadingView.root.isVisible = false
                binding.specieErrorState.isVisible = false
            }
            is SpecieDetailViewState.Error -> {
                specieAdapter.reset()
                binding.emptyView.isVisible = false
                binding.specieLoadingView.root.isVisible = false
                binding.specieErrorState.isVisible = true
                binding.specieErrorState.setCaption(state.message)
            }
            SpecieDetailViewState.Loading -> {
                specieAdapter.reset()
                binding.emptyView.isVisible = false
                binding.specieLoadingView.root.isVisible = true
                binding.specieErrorState.isVisible = false
            }
        }
    }

    fun hide() {
        binding.root.isVisible = false
    }

    fun retryIntent(url: String): Flow<RetryFetchSpecie> =
        binding.specieErrorState.clicks.map { RetryFetchSpecie(url) }

    override val intents: Flow<CharacterDetailViewIntent>
        get() = emptyFlow()
}
