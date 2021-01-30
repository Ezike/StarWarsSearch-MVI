package com.ezike.tobenna.starwarssearch.presentation.mvi.base

interface ViewStateReducer<S : ScreenState, R : ViewResult> {
    fun reduce(previous: S, result: R): S
}
