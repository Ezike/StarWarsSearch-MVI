package com.ezike.tobenna.starwarssearch.character_search.ui.search

import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

/**
 * Keeps a reference to the last query from EditText
 */
object DistinctText {
    var text: String = ""
}

/**
 * checks that string emissions from an EditText for example,
 * are distinct
 */
val Flow<String>.checkDistinct: Flow<String>
    get() {
        return this.filter { string ->
            DistinctText.text != string
        }.onEach { value ->
            DistinctText.text = value
        }.onCompletion {
            DistinctText.text = ""
        }
    }

const val DEBOUNCE_PERIOD: Long = 300L

val EditText.textChanges: Flow<String>
    get() = this.textChanges()
        .skipInitialValue()
        .debounce(DEBOUNCE_PERIOD)
        .map(CharSequence::toString)
        .map(String::trim)
        .conflate()
