package com.ezike.tobenna.starwarssearch.character_search.views.search

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent

data class Search(val query: String) : ViewIntent

class SearchBarView(searchBar: EditText, dispatch: DispatchIntent) {

    init {
        searchBar.doOnTextChanged { text: CharSequence?, _, _, _ ->
            val query: String? = text?.trim().toString()
            if (query != null && query != DistinctText.text) {
                dispatch(Search(query))
                DistinctText.text = query
            }
        }
    }
}

/**
 * Object to hold last search query
 * Prevents emission of search results on config change,
 * or each time the search bar is created
 */
private object DistinctText {
    var text: String = Integer.MIN_VALUE.toString()
}
