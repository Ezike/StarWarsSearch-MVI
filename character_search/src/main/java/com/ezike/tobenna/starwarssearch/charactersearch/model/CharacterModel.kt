package com.ezike.tobenna.starwarssearch.charactersearch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel(
    val name: String,
    val birthYear: String,
    val heightCm: String,
    val url: String
) : Parcelable
