package com.ezike.tobenna.starwarssearch.character_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterModel(
    val name: String,
    val birthYear: String,
    val height: String,
    val url: String
) : Parcelable
