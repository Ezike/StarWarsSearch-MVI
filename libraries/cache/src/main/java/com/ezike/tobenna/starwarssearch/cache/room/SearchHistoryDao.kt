package com.ezike.tobenna.starwarssearch.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ezike.tobenna.starwarssearch.cache.model.CharacterCacheModel

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(characterCacheModel: CharacterCacheModel)

    @Query("SELECT * FROM SEARCH_HISTORY ORDER BY lastUpdated DESC")
    suspend fun recentSearches(): List<CharacterCacheModel>

    @Query("DELETE FROM SEARCH_HISTORY")
    suspend fun clearHistory()
}
