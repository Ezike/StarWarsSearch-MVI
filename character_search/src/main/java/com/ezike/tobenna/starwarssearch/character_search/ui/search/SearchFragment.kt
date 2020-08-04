package com.ezike.tobenna.starwarssearch.character_search.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.CharacterSearchViewModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchCharacterViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState.SearchCharacterViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.textChanges
import com.ezike.tobenna.starwarssearch.core.ext.observe
import com.ezike.tobenna.starwarssearch.core.ext.onBackPress
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),
    MVIView<SearchViewIntent, SearchViewState> {

    private val viewModel: CharacterSearchViewModel by viewModels()

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override val intents: Flow<SearchViewIntent>
        get() = merge(
            binding.searchBar.textChanges.map(SearchCharacterViewIntent::Search),
            binding.searchResult.intents, binding.recentSearch.intents,
            binding.searchResult.retryIntent(binding.searchBar.textChanges)
        )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.processIntent(intents)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        onBackPress {
            if (binding.searchBar.text.isNotEmpty()) {
                binding.searchBar.text.clear()
            } else {
                requireActivity().finish()
            }
        }
    }

    override fun render(state: SearchViewState) {
        when (state) {
            SearchViewState.Idle -> {
            }
            is SearchHistoryViewState -> {
                binding.recentSearch.render(state)
                binding.searchResult.hide()
            }
            is SearchCharacterViewState -> {
                binding.searchResult.render(state)
                binding.recentSearch.hide()
            }
        }
    }
}
