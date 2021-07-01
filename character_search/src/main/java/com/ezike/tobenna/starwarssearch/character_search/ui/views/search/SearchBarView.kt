package com.ezike.tobenna.starwarssearch.character_search.ui.views.search

import android.widget.EditText
import androidx.lifecycle.LifecycleCoroutineScope
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchIntent
import com.ezike.tobenna.starwarssearch.presentation_android.StatelessUIComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

class SearchBarView(
    searchBar: EditText,
    coroutineScope: LifecycleCoroutineScope
) : StatelessUIComponent() {

    init {
        searchBar.textChanges
            .onEach { query -> sendIntent(SearchIntent(query)) }
            .launchIn(coroutineScope)
    }
}
