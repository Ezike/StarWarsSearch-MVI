package com.ezike.tobenna.starwarssearch.cache.room

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class TypeConverter {

    private val moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun toList(value: String): List<String>? {
        val type: ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        val type: ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return adapter.toJson(value)
    }
}
