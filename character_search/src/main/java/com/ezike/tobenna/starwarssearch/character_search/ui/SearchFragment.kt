package com.ezike.tobenna.starwarssearch.character_search.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.character_search.navigation.Navigator
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchComponentManager
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchScreenState
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
    lateinit var navigator: Navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val componentManager: CharacterSearchComponentManager by viewModels()

        val binding = FragmentSearchBinding.bind(view)

        handleBackPress(binding.searchBar)

        componentManager.run {
            subscribe(
                component = SearchBarView(
                    searchBar = binding.searchBar,
                    coroutineScope = viewScope
                )
            )
            subscribe(
                component = SearchHistoryView(
                    view = binding.recentSearch,
                    navigationAction = navigator::openCharacterDetail
                ),
                stateTransform = SearchScreenState::searchHistoryState
            )
            subscribe(
                component = SearchResultView(
                    view = binding.searchResult,
                    query = binding.searchBar.lazyText,
                    navigationAction = navigator::openCharacterDetail
                ),
                stateTransform = SearchScreenState::searchResultState
            )

            disposeAll(viewLifecycleOwner)
        }
    }

    private fun handleBackPress(editText: EditText) {
        onBackPress {
            if (editText.text.isNotEmpty()) {
                editText.text.clear()
            } else {
                requireActivity().finish()
            }
        }
    }
}
