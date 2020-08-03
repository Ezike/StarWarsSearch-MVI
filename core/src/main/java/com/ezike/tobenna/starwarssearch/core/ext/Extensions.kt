package com.ezike.tobenna.starwarssearch.core.ext

val Throwable.errorMessage: String
    get() = message ?: localizedMessage ?: "An error occurred 😩"
