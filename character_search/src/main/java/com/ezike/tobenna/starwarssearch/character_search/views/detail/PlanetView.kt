package com.ezike.tobenna.starwarssearch.character_search.views.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.PlanetViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent.RetryFetchPlanet
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.PlanetDetailViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class PlanetView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<CharacterDetailViewIntent, PlanetDetailViewState> {

    private var binding: PlanetViewLayoutBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = PlanetViewLayoutBinding.inflate(inflater, this, true)
    }

    fun renderLoading() {
        render(PlanetDetailViewState.Loading)
    }

    override fun render(state: PlanetDetailViewState) {
        binding.root.isVisible = true
        when (state) {
            is PlanetDetailViewState.Success -> {
                binding.planetCardView.isVisible = true
                binding.planetLoadingView.root.isVisible = false
                binding.planetErrorState.isVisible = false
                binding.planetName.text = context.getString(R.string.planet_name, state.planet.name)
                val population: String = try {
                    context.getString(R.string.population, state.planet.population.toLong())
                } catch (e: Exception) {
                    context.getString(R.string.population_not_available)
                }
                binding.planetPopulation.text = population
            }
            is PlanetDetailViewState.Error -> {
                binding.planetCardView.isVisible = false
                binding.planetLoadingView.root.isVisible = false
                binding.planetErrorState.isVisible = true
                binding.planetErrorState.setCaption(state.message)
            }
            PlanetDetailViewState.Loading -> {
                binding.planetCardView.isVisible = false
                binding.planetLoadingView.root.isVisible = true
                binding.planetErrorState.isVisible = false
            }
        }
    }

    fun hide() {
        binding.root.isVisible = false
    }

    fun retryIntent(url: String): Flow<RetryFetchPlanet> =
        binding.planetErrorState.clicks.map { RetryFetchPlanet(url) }

    override val intents: Flow<CharacterDetailViewIntent>
        get() = emptyFlow()
}
