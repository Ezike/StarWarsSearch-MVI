package com.ezike.tobenna.starwarssearch.character_search.model

import java.math.BigDecimal
import java.math.RoundingMode

internal val String.toInches: String
    get() = (BigDecimal(this.toDouble() * 0.393701).setScale(3, RoundingMode.HALF_EVEN)).toString()

internal val String.resolveLong: Long
    get() = if (this.contains("unknown", ignoreCase = true)) 0L else this.toLong()
