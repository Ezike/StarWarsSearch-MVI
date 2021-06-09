package com.ezike.tobenna.starwarssearch.character_detail.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.databinding.FragmentCharacterDetailBinding
import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewState
import com.ezike.tobenna.starwarssearch.character_detail.presentation.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_detail.presentation.DetailComponentManager
import com.ezike.tobenna.starwarssearch.character_detail.views.DetailErrorView
import com.ezike.tobenna.starwarssearch.character_detail.views.FilmView
import com.ezike.tobenna.starwarssearch.character_detail.views.PlanetView
import com.ezike.tobenna.starwarssearch.character_detail.views.ProfileView
import com.ezike.tobenna.starwarssearch.character_detail.views.ProfileViewState
import com.ezike.tobenna.starwarssearch.character_detail.views.SpecieView
import com.ezike.tobenna.starwarssearch.core.ext.NavigateBack
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation_android.AssistedCreator
import com.ezike.tobenna.starwarssearch.presentation_android.assistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class CharacterDetailComponentManager @AssistedInject constructor(
    stateMachine: CharacterDetailViewStateMachine.Factory,
    @Assisted character: CharacterDetailModel
) : DetailComponentManager(stateMachine.create(character)) {

    @AssistedFactory
    interface Creator : AssistedCreator<CharacterDetailModel, CharacterDetailComponentManager>
}

data class LoadCharacterDetailIntent(val character: CharacterDetailModel) : ViewIntent

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    @Inject
    lateinit var goBack: NavigateBack

    @Inject
    lateinit var creator: CharacterDetailComponentManager.Creator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CharacterDetailFragmentArgs by navArgs()

        val componentManager: CharacterDetailComponentManager by viewModels {
            assistedFactory(creator, args.character)
        }

        val binding = FragmentCharacterDetailBinding.bind(view)

        componentManager.run {
            subscribe(
                component = PlanetView(
                    view = binding.planetView,
                    characterUrl = args.character.url
                ),
                stateTransform = CharacterDetailViewState::planetViewState
            )
            subscribe(
                component = FilmView(
                    view = binding.filmView,
                    characterUrl = args.character.url
                ),
                stateTransform = CharacterDetailViewState::filmViewState
            )
            subscribe(
                component = SpecieView(
                    view = binding.specieView,
                    characterUrl = args.character.url
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
                stateTransform = { ProfileViewState(it.character) }
            )

            disposeAll(viewLifecycleOwner)
        }
    }
}
