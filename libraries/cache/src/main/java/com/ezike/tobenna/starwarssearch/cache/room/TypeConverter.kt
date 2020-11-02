package com.ezike.tobenna.starwarssearch.cache.room

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class TypeConverter {

    private val moshi: Moshi = Moshi.Builder().build()

    private val adapter: JsonAdapter<List<String>> by lazy {
        val type: ParameterizedType =
            Types.newParameterizedType(List::class.java, String::class.java)
        moshi.adapter(type)
    }

    @TypeConverter
    fun toList(value: String): List<String>? {
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        return adapter.toJson(value)
    }
}
