package com.ezike.tobenna.starwarssearch.character_search.views.search

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.views.rememberState
import com.ezike.tobenna.starwarssearch.presentation.mvi.DispatchIntent
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

data class SearchIntent(val query: String) : ViewIntent

@Preview
@Composable
fun UserInputPreview() {
    SearchBar()
}

@Composable
fun SearchBar() {
    val (value, onChange) = rememberState(state = TextFieldValue())
    Surface {
        Column {
            SearchBar(value = value, onChange = onChange)
            Divider(
                modifier = Modifier.padding(top = 16.dp),
                thickness = 4.dp,
                color = colorResource(R.color.divider_bg)
            )
        }
    }
}

@Composable
private fun SearchBar(
    value: TextFieldValue,
    onChange: (TextFieldValue) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
            .background(
                colorResource(R.color.search_bar_bg_color),
                RoundedCornerShape(8.dp)
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .preferredHeight(24.dp)
        )
        BasicTextField(
            modifier = Modifier
                .preferredHeight(48.dp)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start = 45.dp, end = 16.dp, top = 10.dp),
            singleLine = true,
            value = value,
            onValueChange = onChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )
        if (value.text.isEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 48.dp),
                text = stringResource(id = R.string.search),
                color = colorResource(id = R.color.search_color)
            )
        }
    }
}

class SearchBarView(
    searchBar: EditText,
    viewScope: LifecycleCoroutineScope,
    dispatch: DispatchIntent
) {

    init {
        searchBar.textChanges
            .onEach { query -> dispatch(SearchIntent(query)) }
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
