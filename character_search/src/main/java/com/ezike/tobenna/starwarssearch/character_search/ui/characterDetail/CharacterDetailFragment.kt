package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail

import android.os.Bundle
import android.util.Log
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
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.FilmDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.PlanetDetailViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import com.ezike.tobenna.starwarssearch.core.ext.observe
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf

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
        viewModel.processIntent(intents)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    override fun render(state: CharacterDetailViewState) {
        Log.d("detailss", "$state")
        when (state) {
            is PlanetDetailViewState -> {
                binding.detailErrorState.isVisible = false
                binding.planetView.isVisible = true
                binding.planetView.render(state)
            }
            is SpecieDetailViewState -> {
                binding.detailErrorState.isVisible = false
            }
            is FilmDetailViewState -> {
                binding.detailErrorState.isVisible = false
            }
            CharacterDetailViewState.Idle -> {
                binding.detailErrorState.isVisible = false
            }
            is CharacterDetailViewState.ProfileLoaded -> {
                binding.profileView.render(state)
                binding.detailErrorState.isVisible = false
            }
            is CharacterDetailViewState.FetchDetailError -> {
                binding.planetView.hide()
                binding.detailErrorState.isVisible = true
                binding.detailErrorState.setCaption(state.message)
                binding.detailErrorState.setTitle(
                    getString(R.string.error_fetching_details, args.character.name)
                )
            }
        }
    }

    override val intents: Flow<CharacterDetailViewIntent>
        get() = flowOf(LoadCharacterDetail(args.character)).distinctUntilChanged()
}
