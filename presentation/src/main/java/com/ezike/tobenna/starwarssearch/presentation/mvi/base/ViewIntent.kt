package com.ezike.tobenna.starwarssearch.presentation.mvi.base

interface ViewIntent
object NoOpIntent : ViewIntent

typealias DispatchIntent = (ViewIntent) -> Unit
