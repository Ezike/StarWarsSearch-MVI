package com.ezike.tobenna.starwarssearch.presentation.mvi.base

public interface MVIPresenter<SC : ScreenState> {
    public fun <VS : ViewState> subscribe(
        component: Subscriber<VS>,
        transform: StateTransform<SC, VS>
    )
    public fun <VS : ViewState> subscribe(
        component: Subscriber<VS>
    )
}
