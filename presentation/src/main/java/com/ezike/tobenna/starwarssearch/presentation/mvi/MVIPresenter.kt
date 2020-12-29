package com.ezike.tobenna.starwarssearch.presentation.mvi

import kotlinx.coroutines.flow.StateFlow

interface MVIPresenter<SC : ScreenState> {
    fun processIntent(intent: ViewIntent)
    val viewState: StateFlow<SC>
}
