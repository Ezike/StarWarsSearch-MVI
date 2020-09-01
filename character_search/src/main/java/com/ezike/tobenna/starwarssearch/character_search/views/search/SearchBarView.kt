package com.ezike.tobenna.starwarssearch.character_search.views.search

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent

data class Search(val query: String) : ViewIntent

class SearchBarView(searchBar: EditText, dispatch: DispatchIntent) {

    init {
        searchBar.doOnTextChanged { text: CharSequence?, _, _, _ ->
            if (text != null) {
                dispatch(Search(text.trim().toString()))
            }
        }
    }
}
