package com.ezike.tobenna.starwarssearch.presentation.mvi.base

/**
 * Represents the state of a screen that may comprise of several [UIComponent]
 */
public interface ScreenState

/**
 * Represents the state of a [UIComponent]
 */
public interface ViewState
private object NoOpViewState : ViewState

/**
 * Function to map from a [ScreenState] to [ViewState]
 */
public typealias StateTransform<S, V> = (S) -> V

@Suppress("UNCHECKED_CAST", "FunctionName")
public fun <S : ScreenState, V : ViewState> NoOpTransform(): StateTransform<S, V> =
    { NoOpViewState as V }
