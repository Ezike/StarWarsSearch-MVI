package com.ezike.tobenna.starwarssearch.presentation.mvi

import kotlinx.coroutines.flow.Flow

interface MVIPresenter<out S : ViewState, in I : ViewIntent> {
    val viewState: Flow<S>
    fun processIntent(intent: I)
}
