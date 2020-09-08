package com.ezike.tobenna.starwarssearch.character_search.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.character_search.navigation.NavigationDispatcher
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.CharacterSearchViewModel
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchBarView
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchHistoryView
import com.ezike.tobenna.starwarssearch.character_search.views.search.SearchResultView
import com.ezike.tobenna.starwarssearch.core.ext.onBackPress
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

object LoadSearchHistory : ViewIntent

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var navigator: NavigationDispatcher

    private val viewModel: CharacterSearchViewModel by viewModels()

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackPress()

        SearchBarView(binding.searchBar, viewModel::processIntent)
        viewModel.run {
            subscribe(
                SearchHistoryView(
                    binding.recentSearch,
                    viewModel::processIntent,
                    navigator::openCharacterDetail
                )
            ) { screenState -> screenState.searchHistoryState }
            subscribe(
                SearchResultView(
                    binding.searchResult,
                    viewModel::processIntent,
                    binding.searchBar.lazyText,
                    navigator::openCharacterDetail
                )
            ) { screenState -> screenState.searchResultState }
        }
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
