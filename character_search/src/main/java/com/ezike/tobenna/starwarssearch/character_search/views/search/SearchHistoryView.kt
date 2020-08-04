package com.ezike.tobenna.starwarssearch.character_search.views.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchHistoryBinding
import com.ezike.tobenna.starwarssearch.character_search.navigation.NavigationDispatcher
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchHistoryViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.mvi.SearchViewState.SearchHistoryViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter.SearchHistoryAdapter
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class SearchHistoryView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<SearchHistoryViewIntent, SearchHistoryViewState> {

    @Inject
    lateinit var searchHistoryAdapter: SearchHistoryAdapter

    @Inject
    lateinit var navigator: Provider<NavigationDispatcher>

    private var binding: LayoutSearchHistoryBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = LayoutSearchHistoryBinding.inflate(inflater, this, true)
        binding.searchHistory.adapter = searchHistoryAdapter.apply {
            clickListener = navigator.get()::openCharacterDetail
        }
    }

    private val clearHistoryIntent: Flow<SearchHistoryViewIntent>
        get() = binding.clearHistory.clicks().map { SearchHistoryViewIntent.ClearSearchHistory }

    fun hide() {
        binding.recentSearchGroup.isVisible = false
        binding.searchHistoryPrompt.isVisible = false
        binding.searchHistory.isInvisible = true
    }

    override fun render(state: SearchHistoryViewState) {
        when (state) {
            is SearchHistoryViewState.SearchHistoryLoaded -> {
                searchHistoryAdapter.submitList(state.history)
                with(binding) {
                    searchHistoryPrompt.isVisible = false
                    recentSearchGroup.isVisible = true
                    searchHistory.isVisible = true
                }
            }
            SearchHistoryViewState.SearchHistoryEmpty -> {
                searchHistoryAdapter.reset()
                with(binding) {
                    searchHistoryPrompt.isVisible = true
                    recentSearchGroup.isVisible = false
                    searchHistory.isInvisible = true
                }
            }
        }
    }

    override val intents: Flow<SearchHistoryViewIntent>
        get() = clearHistoryIntent
}
