package com.ezike.tobenna.starwarssearch.character_search.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.character_search.navigation.NavigationDispatcher
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchComponentManager
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchBarView
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchHistoryView
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchResultView
import com.ezike.tobenna.starwarssearch.core.ext.lazyText
import com.ezike.tobenna.starwarssearch.core.ext.onBackPress
import com.ezike.tobenna.starwarssearch.core.ext.viewScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterSearchComponentManager @Inject constructor(
    searchStateMachine: SearchStateMachine
) : SearchComponentManager(searchStateMachine)

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var navigator: NavigationDispatcher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val componentManager: CharacterSearchComponentManager by viewModels()

        val binding = FragmentSearchBinding.bind(view)

        handleBackPress(binding)

        componentManager.run {
            subscribe(SearchBarView(binding.searchBar, viewScope))
            subscribe(
                SearchHistoryView(
                    binding.recentSearch,
                    navigator::openCharacterDetail
                )
            ) { screenState -> screenState.searchHistoryState }
            subscribe(
                SearchResultView(
                    binding.searchResult,
                    binding.searchBar.lazyText,
                    navigator::openCharacterDetail
                )
            ) { screenState -> screenState.searchResultState }

            disposeAll(viewLifecycleOwner)
        }
    }

    private fun handleBackPress(binding: FragmentSearchBinding) {
        onBackPress {
            if (binding.searchBar.text.isNotEmpty()) {
                binding.searchBar.text.clear()
            } else {
                requireActivity().finish()
            }
        }
    }
}
