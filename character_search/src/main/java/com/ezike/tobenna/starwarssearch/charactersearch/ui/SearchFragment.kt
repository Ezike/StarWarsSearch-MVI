package com.ezike.tobenna.starwarssearch.charactersearch.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ezike.tobenna.starwarssearch.charactersearch.R
import com.ezike.tobenna.starwarssearch.charactersearch.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.charactersearch.navigation.Navigator
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.viewstate.SearchScreenState
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.history.SearchHistoryView
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.result.SearchResultView
import com.ezike.tobenna.starwarssearch.charactersearch.ui.views.search.SearchBarView
import com.ezike.tobenna.starwarssearch.core.ext.lazyText
import com.ezike.tobenna.starwarssearch.core.ext.onBackPress
import com.ezike.tobenna.starwarssearch.core.ext.viewScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var navigator: Navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: CharacterSearchViewModel by viewModels()

        val binding = FragmentSearchBinding.bind(view)

        handleBackPress(binding.searchBar)

        viewModel.run {
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
