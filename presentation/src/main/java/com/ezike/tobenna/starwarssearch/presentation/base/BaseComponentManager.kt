package com.ezike.tobenna.starwarssearch.presentation.base

public interface BaseComponentManager<SC : ScreenState> {
    public fun <VS : ViewState> subscribe(
        component: Subscriber<VS>,
        stateTransform: StateTransform<SC, VS>
    )
    public fun <VS : ViewState> subscribe(
        component: Subscriber<VS>
    )
}
