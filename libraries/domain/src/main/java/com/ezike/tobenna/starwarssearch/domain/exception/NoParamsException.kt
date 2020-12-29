package com.ezike.tobenna.starwarssearch.domain.exception

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * An exception thrown when a use case parameter is null
 */
internal class NoParamsException(errorMessage: String = noParamMessage) :
    IllegalArgumentException(errorMessage)

const val noParamMessage: String = "Your params cannot be null for this use case"

@OptIn(ExperimentalContracts::class)
fun <T : Any> requireParams(value: T?): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) {
        throw NoParamsException()
    } else {
        return value
    }
}
