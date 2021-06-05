package com.example.quizapp.util

import com.google.gson.Gson
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import java.util.*
import java.lang.reflect.Type

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
class StringConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun stringToList(data: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.getType()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<String>): String? {
        return gson.toJson(someObjects)
    }
}