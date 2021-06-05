package com.example.quizapp.data.repository

import com.example.quizapp.data.api.ApiService
import com.example.quizapp.data.db.AppDatabase
import com.example.quizapp.data.model.Quiz
import javax.inject.Inject

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
class QuizRepository @Inject constructor(private val apiService: ApiService, private var appDatabase: AppDatabase) {

    suspend fun getQuiz() = apiService.getQuiz()

    fun insertQuiz(quiz: List<Quiz>) {
        appDatabase.quizDao().delete()
        appDatabase.quizDao().insert(quiz) }

    fun getAllQuiz() = appDatabase.quizDao().selectQuiz()
}