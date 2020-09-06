package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentCharacterDetailBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewModel
import com.ezike.tobenna.starwarssearch.character_search.views.detail.DetailErrorView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.FilmView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.PlanetView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.ProfileView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.ProfileViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.SpecieView
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import dagger.hilt.android.AndroidEntryPoint

data class LoadCharacterDetailIntent(val character: CharacterModel) : ViewIntent

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private val viewModel: CharacterDetailViewModel by viewModels()

    private val args: CharacterDetailFragmentArgs by navArgs()

    private val binding: FragmentCharacterDetailBinding by viewBinding(
        FragmentCharacterDetailBinding::bind
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Making sure this doesn't emit again on config change.
        if (savedInstanceState == null) {
            viewModel.processIntent(LoadCharacterDetailIntent(args.character))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            subscribe(
                PlanetView(binding.planetView, args.character.url, viewModel::processIntent)
            ) { screenState -> screenState.planetViewState }
            subscribe(
                FilmView(binding.filmView, args.character.url, viewModel::processIntent)
            ) { screenState -> screenState.filmViewState }
            subscribe(
                SpecieView(binding.specieView, args.character.url, viewModel::processIntent)
            ) { screenState -> screenState.specieViewState }
            subscribe(
                DetailErrorView(binding.detailErrorState, args.character, viewModel::processIntent)
            ) { screenState -> screenState.errorViewState }
            subscribe(ProfileView(binding.profileView)) { screenState ->
                ProfileViewState(screenState.character)
            }
        }
    }
}
