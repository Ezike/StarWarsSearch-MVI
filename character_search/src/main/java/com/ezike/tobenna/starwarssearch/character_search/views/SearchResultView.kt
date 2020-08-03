package com.ezike.tobenna.starwarssearch.character_search.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.LayoutSearchResultBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchCharacterViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.mvi.SearchViewState.SearchCharacterViewState
import com.ezike.tobenna.starwarssearch.character_search.ui.adapter.SearchResultAdapter
import com.ezike.tobenna.starwarssearch.core.ext.getImage
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SearchResultView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<SearchCharacterViewIntent, SearchCharacterViewState> {

    @Inject
    lateinit var searchResultAdapter: SearchResultAdapter

    private var binding: LayoutSearchResultBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = LayoutSearchResultBinding.inflate(inflater, this, true)
        binding.characters.adapter = searchResultAdapter
    }

    private val saveSearchIntent: Flow<SearchCharacterViewIntent>
        get() = searchResultAdapter.clicks.map(SearchCharacterViewIntent::SaveSearch)

    fun retryIntent(text: Flow<String>): Flow<SearchCharacterViewIntent> =
        binding.emptyState.clicks.combine(text) { _, query ->
            SearchCharacterViewIntent.Search(query)
        }

    fun hide() {
        searchResultAdapter.reset()
        binding.characters.isInvisible = true
        binding.progressBar.isVisible = false
        binding.emptyState.isVisible = false
    }

    override fun render(state: SearchCharacterViewState) {
        when (state) {
            SearchCharacterViewState.Searching -> {
                with(binding) {
                    progressBar.isVisible = !characters.isVisible
                    emptyState.isVisible = false
                }
            }
            is SearchCharacterViewState.SearchResultLoaded -> {
                with(binding) {
                    progressBar.isVisible = false
                    characters.isInvisible = state.characters.isEmpty()
                    emptyState.isVisible = state.characters.isEmpty()
                    if (emptyState.isVisible) {
                        emptyState.setImage(context.getImage(R.drawable.ic_empty))
                        emptyState.setTitle(context.getString(R.string.no_data))
                        emptyState.resetCaption()
                        emptyState.isButtonVisible = false
                    }
                }
                searchResultAdapter.submitList(state.characters)
            }
            is SearchCharacterViewState.Error -> {
                with(binding) {
                    characters.isInvisible = true
                    progressBar.isVisible = false
                    emptyState.isVisible = true
                    if (emptyState.isVisible) {
                        emptyState.setImage(context.getImage(R.drawable.ic_error_page_2))
                        emptyState.setCaption(state.message)
                        emptyState.setTitle(context.getString(R.string.an_error_occurred))
                        emptyState.isButtonVisible = true
                    }
                }
            }
        }
    }

    override val intents: Flow<SearchCharacterViewIntent>
        get() = saveSearchIntent
}
