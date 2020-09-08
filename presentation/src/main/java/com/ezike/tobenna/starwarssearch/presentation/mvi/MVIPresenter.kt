package com.ezike.tobenna.starwarssearch.presentation.mvi

interface MVIPresenter<SC : ScreenState> {
    fun <VS : ViewState> subscribe(
        component: UIComponent<VS>,
        transform: StateTransform<SC, VS>
    )

    fun processIntent(intent: ViewIntent)
}
