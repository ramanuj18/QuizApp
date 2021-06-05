package com.example.quizapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.data.model.Quiz

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
@Dao
interface QuizDao {

    @Insert
    fun insert(quiz: List<Quiz>)

    @Query("select * from quiz")
    fun selectQuiz(): List<Quiz>

    @Query("delete from quiz")
    fun delete()
}