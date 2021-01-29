package com.ezike.tobenna.starwarssearch.presentation.mvi

interface ViewIntent
object NoOpIntent : ViewIntent

typealias DispatchIntent = (ViewIntent) -> Unit
