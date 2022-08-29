package com.gabo.moviesapp.data.providers.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresTypeConverters {
    @TypeConverter
    fun fromSource(genreIds: List<Int>): String {
        return Gson().toJson(genreIds).toString()
    }

    @TypeConverter
    fun toSource(genreIds: String): List<Int> {
        val listType = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson(genreIds, listType)
    }

}