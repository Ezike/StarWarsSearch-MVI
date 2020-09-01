package com.ezike.tobenna.starwarssearch.character_search.ui.search

import android.widget.EditText

val EditText.texts: String
    get() = this.text.trim().toString()

val EditText.lazyText: () -> String
    get() = { texts }
