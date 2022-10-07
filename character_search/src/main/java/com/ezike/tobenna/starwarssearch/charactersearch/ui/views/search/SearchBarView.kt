package com.ezike.tobenna.starwarssearch.charactersearch.ui.views.search

import android.widget.EditText
import androidx.lifecycle.LifecycleCoroutineScope
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchIntent
import com.ezike.tobenna.starwarssearch.presentation_android.StatelessUIComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
