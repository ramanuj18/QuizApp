package com.example.quizapp.data.model

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
data class QuizResponse(
    val status : String,
    val code : Int,
    val msg : String,
    val result : List<Quiz>
)
