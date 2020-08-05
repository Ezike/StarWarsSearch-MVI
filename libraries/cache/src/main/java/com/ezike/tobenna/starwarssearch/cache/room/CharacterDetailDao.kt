package com.ezike.tobenna.starwarssearch.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ezike.tobenna.starwarssearch.cache.entity.CharacterDetailCacheModel

@Dao
interface CharacterDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterDetailCacheModel: CharacterDetailCacheModel)

    @Query("SELECT * FROM CHARACTER_DETAIL WHERE url = :characterUrl")
    suspend fun fetchCharacter(characterUrl: String): CharacterDetailCacheModel?
}
