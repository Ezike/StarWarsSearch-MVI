package com.ezike.tobenna.starwarssearch.presentation.mvi

/**
 * Represents the state of a screen that may comprise of several [UIComponent]
 */
interface ScreenState

/**
 * Represents the state of a [UIComponent]
 */
interface ViewState

/**
 * Function to map from a [ScreenState] to [ViewState]
 */
typealias StateTransform<S, V> = (S) -> V
