package com.ezike.tobenna.starwarssearch.charactersearch.ui

import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchComponentManager
import com.ezike.tobenna.starwarssearch.charactersearch.presentation.SearchStateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CharacterSearchViewModel @Inject constructor(
    searchStateMachine: SearchStateMachine
) : SearchComponentManager(searchStateMachine)
