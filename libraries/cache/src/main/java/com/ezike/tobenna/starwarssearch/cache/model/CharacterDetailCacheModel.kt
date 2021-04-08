package com.ezike.tobenna.starwarssearch.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CHARACTER_DETAIL")
data class CharacterDetailCacheModel(
    val filmUrls: List<String>,
    val planetUrl: String,
    val speciesUrls: List<String>,
    @PrimaryKey
    val url: String
)
