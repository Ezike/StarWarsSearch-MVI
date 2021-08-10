package com.ezike.tobenna.starwarssearch.character_detail.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.databinding.FragmentCharacterDetailBinding
import com.ezike.tobenna.starwarssearch.character_detail.presentation.viewstate.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.error.DetailErrorView
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.film.FilmView
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.planet.PlanetView
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.profile.ProfileView
import com.ezike.tobenna.starwarssearch.character_detail.ui.views.specie.SpecieView
import com.ezike.tobenna.starwarssearch.core.ext.NavigateBack
import com.ezike.tobenna.starwarssearch.presentation_android.assistedFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    @Inject
    lateinit var goBack: NavigateBack

    @Inject
    lateinit var creator: CharacterDetailViewModel.Creator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CharacterDetailFragmentArgs by navArgs()

        val viewModel: CharacterDetailViewModel by viewModels {
            assistedFactory(creator, args.character)
        }

        val binding = FragmentCharacterDetailBinding.bind(view)

        viewModel.run {
            val characterUrl = args.character.url
            subscribe(
                component = PlanetView(
                    view = binding.planetView,
                    characterUrl = characterUrl
                ),
                stateTransform = CharacterDetailViewState::planetViewState
            )
            subscribe(
                component = FilmView(
                    view = binding.filmView,
                    characterUrl = characterUrl
                ),
                stateTransform = CharacterDetailViewState::filmViewState
            )
            subscribe(
                component = SpecieView(
                    view = binding.specieView,
                    characterUrl = characterUrl
                ),
                stateTransform = CharacterDetailViewState::specieViewState
            )
            subscribe(
                component = DetailErrorView(
                    view = binding.detailErrorState,
                    character = args.character
                ),
                stateTransform = CharacterDetailViewState::errorViewState
            )
            subscribe(
                component = ProfileView(
                    view = binding.profileView,
                    navigateUp = goBack
                ),
                stateTransform = CharacterDetailViewState::profileViewState
            )

            disposeAll(viewLifecycleOwner)
        }
    }
}
