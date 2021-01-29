package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface AssistedCreator<in D, out T> {
    operator fun invoke(data: D): T
}

fun <V : ViewModel, D> assistedFactory(
    creator: AssistedCreator<D, V>,
    data: D
) = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val clazz: ViewModel = creator(data)
        if (!modelClass.isAssignableFrom(clazz::class.java))
            throw IllegalArgumentException("Unknown model class $modelClass")
        try {
            return clazz as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}