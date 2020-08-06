package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentCharacterDetailBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent.LoadCharacterDetail
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent.RetryFetchCharacterDetails
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.FilmDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.PlanetDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import com.ezike.tobenna.starwarssearch.core.ext.observe
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail),
    MVIView<CharacterDetailViewIntent, CharacterDetailViewState> {

    private val viewModel: CharacterDetailViewModel by viewModels()

    private val args: CharacterDetailFragmentArgs by navArgs()

    private val binding: FragmentCharacterDetailBinding by viewBinding(
        FragmentCharacterDetailBinding::bind
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Making sure this doesn't emit again on config change.
        if (savedInstanceState == null) {
            loadCharacterDetail.offer(LoadCharacterDetail(args.character))
        }
        viewModel.processIntent(intents)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    override fun render(state: CharacterDetailViewState) {
        when (state) {
            CharacterDetailViewState.Idle -> {
            }
            is PlanetDetailViewState -> {
                binding.planetView.render(state)
                binding.detailErrorState.isVisible = false
            }
            is SpecieDetailViewState -> {
                binding.specieView.render(state)
                binding.detailErrorState.isVisible = false
            }
            is FilmDetailViewState -> {
                binding.filmView.render(state)
                binding.detailErrorState.isVisible = false
            }
            is CharacterDetailViewState.ProfileLoaded -> {
                binding.profileView.render(state)
                binding.detailErrorState.isVisible = false
            }
            is CharacterDetailViewState.FetchDetailError -> {
                binding.run {
                    planetView.hide(); specieView.hide(); filmView.hide()
                    detailErrorState.isVisible = true
                    detailErrorState.setCaption(state.message)
                    detailErrorState.setTitle(
                        getString(R.string.error_fetching_details, args.character.name)
                    )
                }
            }
            CharacterDetailViewState.Retrying -> {
                binding.run {
                    detailErrorState.isVisible = false
                    filmView.renderLoading()
                    specieView.renderLoading()
                    planetView.renderLoading()
                }
            }
        }
    }

    private val retryFetchDetailIntent: Flow<RetryFetchCharacterDetails>
        get() = binding.detailErrorState.clicks.map {
            RetryFetchCharacterDetails(args.character)
        }

    private val loadCharacterDetail = ConflatedBroadcastChannel<LoadCharacterDetail>()

    override val intents: Flow<CharacterDetailViewIntent>
        get() = merge(
            loadCharacterDetail.asFlow(),
            retryFetchDetailIntent,
            binding.filmView.retryIntent(args.character.url),
            binding.planetView.retryIntent(args.character.url),
            binding.specieView.retryIntent(args.character.url)
        )
}
