package com.ezike.tobenna.starwarssearch.presentation.mvi.base

public interface ViewIntent
internal object NoOpIntent : ViewIntent

public typealias DispatchIntent = (ViewIntent) -> Unit
