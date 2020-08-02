package com.ezike.tobenna.starwarssearch.core.ext

val Throwable.errorMessage: String
    get() = message ?: localizedMessage ?: "An error occurred 😩"

inline fun String.notEmpty(action: (String) -> Unit) {
    if (this.isNotEmpty()) {
        action(this)
    }
}
