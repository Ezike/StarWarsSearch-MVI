package com.ezike.tobenna.starwarssearch.core

import android.content.Context
import android.widget.TextView
import androidx.annotation.StringRes
import java.io.Serializable

sealed interface AppString : Serializable

@JvmInline
value class StringLiteral(
    val value: String
) : AppString

@JvmInline
value class StringResource(
    @StringRes val res: Int
) : AppString

data class ParamString(
    @StringRes val res: Int,
    val params: List<Any>
) : AppString {
    constructor(
        @StringRes res: Int,
        vararg param: Any
    ) : this(
        res = res,
        params = param.toList()
    )
}

var TextView.string: AppString
    get() = StringLiteral(
        value = this.text.toString()
    )
    set(appString) {
        text = string(context, appString)
    }

private fun string(
    context: Context,
    appString: AppString
): String = when (appString) {
    is ParamString -> context.getString(
        appString.res,
        *appString.params.toTypedArray()
    )
    is StringResource -> context.getString(
        appString.res
    )
    is StringLiteral -> appString.value
}
