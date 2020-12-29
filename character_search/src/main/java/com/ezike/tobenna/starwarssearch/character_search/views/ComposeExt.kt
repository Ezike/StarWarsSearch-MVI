package com.ezike.tobenna.starwarssearch.character_search.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> rememberState(state: T): MutableState<T> = remember { mutableStateOf(state) }