package com.ezike.tobenna.starwarssearch.character_search.ui

import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchComponentManager
import com.ezike.tobenna.starwarssearch.character_search.presentation.SearchStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    searchStateMachine: SearchStateMachine
) : SearchComponentManager(searchStateMachine)
