package com.ezike.tobenna.starwarssearch.presentation_android

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

internal inline fun <reified T> T.dispose(owner: LifecycleOwner, crossinline disposeFunction: (T) -> Unit) {
    object : DefaultLifecycleObserver {
        init {
            owner.lifecycle.removeObserver(this)
            owner.lifecycle.addObserver(this)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            disposeFunction(this@dispose)
            owner.lifecycle.removeObserver(this)
            super.onDestroy(owner)
        }
    }
}