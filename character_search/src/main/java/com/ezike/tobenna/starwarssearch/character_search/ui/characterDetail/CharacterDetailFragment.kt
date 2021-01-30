package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentCharacterDetailBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.DetailComponentManager
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.CharacterDetailViewStateMachine
import com.ezike.tobenna.starwarssearch.character_search.views.detail.DetailErrorView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.FilmView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.PlanetView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.ProfileView
import com.ezike.tobenna.starwarssearch.character_search.views.detail.ProfileViewState
import com.ezike.tobenna.starwarssearch.character_search.views.detail.SpecieView
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import com.ezike.tobenna.starwarssearch.presentation_android.AssistedCreator
import com.ezike.tobenna.starwarssearch.presentation_android.assistedFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class CharacterDetailComponentManager @AssistedInject constructor(
    stateMachine: CharacterDetailViewStateMachine.Factory,
    @Assisted character: CharacterModel
) : DetailComponentManager(stateMachine.create(character)) {

    @AssistedFactory
    interface Creator : AssistedCreator<CharacterModel, CharacterDetailComponentManager>

}

data class LoadCharacterDetailIntent(val character: CharacterModel) : ViewIntent

@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

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
                PlanetView(binding.planetView, args.character.url)
            ) { screenState -> screenState.planetViewState }
            subscribe(
                FilmView(binding.filmView, args.character.url)
            ) { screenState -> screenState.filmViewState }
            subscribe(
                SpecieView(binding.specieView, args.character.url)
            ) { screenState -> screenState.specieViewState }
            subscribe(
                DetailErrorView(binding.detailErrorState, args.character)
            ) { screenState -> screenState.errorViewState }
            subscribe(
                ProfileView(binding.profileView)
            ) { screenState -> ProfileViewState(screenState.character) }

            disposeAll(viewLifecycleOwner)
        }
    }
}
