package com.ezike.tobenna.starwarssearch.character_search.ui

import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.widget.textChanges

const val DEBOUNCE_PERIOD: Long = 300L

val EditText.textChanges: Flow<String>
    get() = this.textChanges()
        .skipInitialValue()
        .debounce(DEBOUNCE_PERIOD)
        .map(CharSequence::toString)
        .map(String::trim)
        .distinctUntilChanged()
        .conflate()
