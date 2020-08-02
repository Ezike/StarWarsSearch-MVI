package com.ezike.tobenna.starwarssearch.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel

@Database(
    entities = [CharacterCacheModel::class],
    version = BuildConfig.databaseVersion,
    exportSchema = false
)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract val searchHistoryDao: SearchHistoryDao

    companion object {
        private const val DATABASE_NAME: String = "star_wars_db"
        fun build(context: Context): StarWarsDatabase = Room.databaseBuilder(
            context.applicationContext,
            StarWarsDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}
