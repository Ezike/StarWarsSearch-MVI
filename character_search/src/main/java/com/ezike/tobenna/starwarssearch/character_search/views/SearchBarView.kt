package com.ezike.tobenna.starwarssearch.character_search.views.search

import android.widget.EditText
import androidx.lifecycle.LifecycleCoroutineScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.search.SearchIntent
import com.ezike.tobenna.starwarssearch.presentation_android.StatelessUIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

class SearchBarView(
    searchBar: EditText,
    viewScope: LifecycleCoroutineScope
) : StatelessUIComponent() {

    init {
        searchBar.textChanges
            .onEach { query -> sendIntent(SearchIntent(query)) }
            .launchIn(viewScope)
    }

    private val Flow<String>.checkDistinct: Flow<String>
        get() {
            return this.filter { string ->
                DistinctText.text != string
            }.onEach { value ->
                DistinctText.text = value
            }
        }

    private val EditText.textChanges: Flow<String>
        get() = this.textChanges()
            .skipInitialValue()
            .debounce(DEBOUNCE_PERIOD)
            .map { char -> char.toString().trim() }
            .conflate()
            .checkDistinct

    companion object {
        const val DEBOUNCE_PERIOD: Long = 150L
    }
}

/**
 * Object to hold last search query
 * Prevents emission of search results on config change,
or each time the search bar is created
 */
private object DistinctText {
    var text: String = Integer.MIN_VALUE.toString()
}
