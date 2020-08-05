package com.ezike.tobenna.starwarssearch.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ezike.tobenna.starwarssearch.cache.BuildConfig
import com.ezike.tobenna.starwarssearch.cache.entity.CharacterCacheModel
import com.ezike.tobenna.starwarssearch.cache.entity.CharacterDetailCacheModel

@Database(
    entities = [CharacterCacheModel::class, CharacterDetailCacheModel::class],
    version = BuildConfig.databaseVersion,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {

    abstract val searchHistoryDao: SearchHistoryDao

    abstract val characterDetailDao: CharacterDetailDao

    companion object {
        private const val DATABASE_NAME: String = "star_wars_db"
        fun build(context: Context): StarWarsDatabase = Room.databaseBuilder(
            context.applicationContext,
            StarWarsDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}
