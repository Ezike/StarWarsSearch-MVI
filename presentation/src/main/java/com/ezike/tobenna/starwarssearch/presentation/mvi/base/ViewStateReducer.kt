package com.ezike.tobenna.starwarssearch.presentation.mvi.base

public interface ViewStateReducer<S : ScreenState, R : ViewResult> {
    public fun reduce(oldState: S, result: R): S
}
