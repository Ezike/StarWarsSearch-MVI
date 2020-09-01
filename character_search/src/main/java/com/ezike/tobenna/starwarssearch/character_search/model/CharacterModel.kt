package com.ezike.tobenna.starwarssearch.character_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.math.RoundingMode

@Parcelize
data class CharacterModel(
    val name: String,
    val birthYear: String,
    val heightCm: String,
    val url: String
) : Parcelable {
    val heightInches: String?
        get() = try {
            BigDecimal(heightCm.toDouble() * 0.393701)
                .setScale(1, RoundingMode.HALF_EVEN)
                .toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}
