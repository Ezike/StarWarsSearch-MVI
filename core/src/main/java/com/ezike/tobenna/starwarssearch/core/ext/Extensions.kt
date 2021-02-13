package com.ezike.tobenna.starwarssearch.core.ext

val Throwable.errorMessage: String
    get() = message ?: localizedMessage ?: "An error occurred"

fun <T> init(initializer: () -> T): Lazy<T> =
    lazy(mode = LazyThreadSafetyMode.NONE, initializer)
