package com.ezike.tobenna.starwarssearch.presentation.mvi

interface MVIPresenter<SC : ScreenState> {
    fun <VS : ViewState> subscribe(
        component: Subscriber<VS>,
        transform: StateTransform<SC, VS>
    )
    fun <VS : ViewState> subscribe(
        component: Subscriber<VS>
    )
}
