package com.example.quizapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quizapp.data.db.dao.QuizDao
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.util.StringConverter

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
@Database(entities = [Quiz::class], version = 1, exportSchema = false)
@TypeConverters(StringConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun quizDao(): QuizDao

}