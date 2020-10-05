package com.ezike.tobenna.starwarssearch.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SEARCH_HISTORY")
data class CharacterCacheModel(
    val name: String,
    val birthYear: String,
    val height: String,
    @PrimaryKey
    val url: String
) {
    var lastUpdated: Long = 0L
}
