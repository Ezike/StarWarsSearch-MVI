package com.ezike.tobenna.starwarssearch.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(characterCacheModel: CharacterCacheModel)

    @get:Query("SELECT * FROM SEARCH_HISTORY ORDER BY timeSent DESC")
    val recentSearches: List<CharacterCacheModel>

    @Query("DELETE FROM SEARCH_HISTORY")
    suspend fun clearHistory()
}
