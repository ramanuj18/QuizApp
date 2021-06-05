package com.example.quizapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.quizapp.util.StringConverter

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val q : String,
//    @TypeConverters(StringConverter::class)
    var options : List<String>?=null,
    val correctIndex : Int,
    val correctResponse : String,
    val incorrectResponse : String
)

