package com.ezike.tobenna.starwarssearch.presentation.mvi

interface MVIPresenter<S : ScreenState> {
    fun <V : ViewState> subscribe(
        component: UIComponent<V>,
        transform: StateTransform<S, V>
    )

    fun processIntent(intent: ViewIntent)
}
