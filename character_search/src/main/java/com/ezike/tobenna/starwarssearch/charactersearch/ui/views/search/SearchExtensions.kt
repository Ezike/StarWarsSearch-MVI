package com.ezike.tobenna.starwarssearch.charactersearch.ui.views.search

import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

private val Flow<String>.checkDistinct: Flow<String>
    get() {
        return this.filter { string ->
            DistinctText.text != string
        }.onEach { value ->
            DistinctText.text = value
        }
    }

const val DEBOUNCE_PERIOD: Long = 150L

internal val EditText.textChanges: Flow<String>
    get() = this.textChanges()
        .skipInitialValue()
        .debounce(DEBOUNCE_PERIOD)
        .map { char -> char.toString().trim() }
        .conflate()
        .checkDistinct

/**
 * Object to hold last search query
 * Prevents emission of search results on config change,
or each time the search bar is created
 */
private object DistinctText {
    var text: String = Integer.MIN_VALUE.toString()
}
