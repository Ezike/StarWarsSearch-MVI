package com.ezike.tobenna.starwarssearch.character_search.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.lifecycle.ViewModelInject
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.character_search.navigation.NavigationDispatcher
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchComponentManager
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchBar
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchBarView
import com.ezike.tobenna.starwarssearch.core.ext.onBackPress
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class CharacterSearchViewModel @ViewModelInject constructor(
    searchStateMachine: SearchStateMachine
) : SearchComponentManager(searchStateMachine)

object LoadSearchHistory : ViewIntent

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var navigator: NavigationDispatcher

    private val viewModel: CharacterSearchViewModel by viewModels()

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                SearchBar()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleBackPress()

        // SearchBarView(binding.searchBar, viewScope, viewmodel::processIntent)
        // viewmodel.run {
        //     subscribe(
        //         SearchHistoryView(
        //             binding.recentSearch,
        //             ::processIntent,
        //             navigator::openCharacterDetail
        //         )
        //     ) { (searchHistoryState: SearchHistoryViewState) -> searchHistoryState }
        //     subscribe(
        //         SearchResultView(
        //             binding.searchResult,
        //             ::processIntent,
        //             binding.searchBar.lazyText,
        //             navigator::openCharacterDetail
        //         )
        //     ) { screenState: SearchViewState -> screenState.searchResultState }
        // }
    }

    private fun handleBackPress() {
        onBackPress {
            if (binding.searchBar.text.isNotEmpty()) {
                binding.searchBar.text.clear()
            } else {
                requireActivity().finish()
            }
        }
    }
}
