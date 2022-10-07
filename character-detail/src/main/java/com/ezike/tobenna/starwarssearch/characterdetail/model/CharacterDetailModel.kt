package com.ezike.tobenna.starwarssearch.characterdetail.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDetailModel(
    val name: String,
    val birthYear: String,
    val heightCm: String,
    val url: String
) : Parcelable
