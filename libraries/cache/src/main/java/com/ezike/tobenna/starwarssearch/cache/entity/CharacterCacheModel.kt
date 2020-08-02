package com.ezike.tobenna.starwarssearch.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SEARCH_HISTORY")
data class CharacterCacheModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val birthYear: String,
    val height: String,
    val url: String
) {
    var timeSent: Long = 0L
}
