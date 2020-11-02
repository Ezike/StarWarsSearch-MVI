package com.ezike.tobenna.starwarssearch.presentation.mvi.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Atomic<T>(@Volatile private var _value: T) : Mutex by Mutex() {

    val value: T
        get() = _value

    suspend fun update(newValue: T) {
        (this as Mutex).withLock { _value = newValue }
    }
}
