package com.ezike.tobenna.starwarssearch.character_search.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.FragmentSearchBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.CharacterSearchViewModel
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchCharacterViewState
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.adapter.SearchHistoryAdapter
import com.ezike.tobenna.starwarssearch.character_search.ui.adapter.SearchResultAdapter
import com.ezike.tobenna.starwarssearch.core.ext.getDrawable
import com.ezike.tobenna.starwarssearch.core.ext.observe
import com.ezike.tobenna.starwarssearch.core.viewBinding.viewBinding
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.textChanges

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),
    MVIView<SearchViewIntent, SearchViewState> {

    @Inject
    lateinit var searchHistoryAdapter: SearchHistoryAdapter

    @Inject
    lateinit var searchResultAdapter: SearchResultAdapter

    private val viewModel: CharacterSearchViewModel by viewModels()

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override val intents: Flow<SearchViewIntent>
        get() = merge(searchIntent, retrySearchIntent, clearHistoryIntent, saveSearchIntent)

    private val searchIntent: Flow<SearchViewIntent>
        get() = binding.searchBar.textChanges(emitImmediately = false)
            .debounce(DEBOUNCE_PERIOD)
            .map(CharSequence::toString)
            .map(SearchViewIntent::Search)

    private val retrySearchIntent: Flow<SearchViewIntent>
        get() = binding.emptyState.clicks
            .debounce(DEBOUNCE_PERIOD)
            .map { SearchViewIntent.Search(binding.searchBar.text.toString().trim()) }

    private val saveSearchIntent: Flow<SearchViewIntent.SaveSearch>
        get() = searchResultAdapter.clicks.map(SearchViewIntent::SaveSearch)

    private val clearHistoryIntent: Flow<SearchViewIntent.ClearSearchHistory>
        get() = binding.clearHistory.clicks()
            .debounce(DEBOUNCE_PERIOD)
            .map { SearchViewIntent.ClearSearchHistory }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.processIntent(intents)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        binding.searchHistory.adapter = searchHistoryAdapter
        binding.characters.adapter = searchResultAdapter
    }

    override fun render(state: SearchViewState) {
        when (state) {
            SearchViewState.Idle -> {
                searchResultAdapter.reset()
                with(binding) {
                    searchHistoryPrompt.isVisible = true
                    recentSearchGroup.isVisible = false
                    progressBar.isVisible = false
                    emptyState.isVisible = false
                    characters.isInvisible = true
                }
            }
            is SearchHistoryViewState.SearchHistoryLoaded -> {
                searchHistoryAdapter.submitList(state.history)
                searchResultAdapter.reset()
                with(binding) {
                    searchHistoryPrompt.isVisible = false
                    recentSearchGroup.isVisible = true
                    progressBar.isVisible = false
                    emptyState.isVisible = false
                    characters.isInvisible = true
                }
            }
            SearchHistoryViewState.SearchHistoryEmpty -> {
                searchResultAdapter.reset()
                with(binding) {
                    searchHistoryPrompt.isVisible = true
                    recentSearchGroup.isVisible = false
                    progressBar.isVisible = false
                    emptyState.isVisible = false
                    characters.isInvisible = true
                }
            }
            SearchCharacterViewState.Searching -> {
                with(binding) {
                    searchHistoryPrompt.isVisible = false
                    recentSearchGroup.isVisible = false
                    progressBar.isVisible = !characters.isVisible
                    emptyState.isVisible = false
                }
            }
            is SearchCharacterViewState.SearchResultLoaded -> {
                with(binding) {
                    searchHistoryPrompt.isVisible = false
                    recentSearchGroup.isVisible = false
                    progressBar.isVisible = false
                    characters.isVisible = state.characters.isNotEmpty()
                    emptyState.isVisible = state.characters.isEmpty()
                    if (emptyState.isVisible) {
                        emptyState.setImage(getDrawable(R.drawable.ic_empty))
                        emptyState.setTitle(getString(R.string.no_data))
                        emptyState.resetCaption()
                        emptyState.isButtonVisible = false
                    }
                }
                searchResultAdapter.submitList(state.characters)
            }
            is SearchCharacterViewState.Error -> {
                with(binding) {
                    searchHistoryPrompt.isVisible = false
                    recentSearchGroup.isVisible = false
                    characters.isVisible = false
                    progressBar.isVisible = false
                    emptyState.isVisible = true
                    if (emptyState.isVisible) {
                        emptyState.setImage(getDrawable(R.drawable.ic_error_page_2))
                        emptyState.setCaption(state.message)
                        emptyState.setTitle(getString(R.string.an_error_occurred))
                        emptyState.isButtonVisible = true
                    }
                }
            }
        }
    }

    companion object {
        const val DEBOUNCE_PERIOD: Long = 300L
    }
}
