package com.example.quizapp.data.api

import com.example.quizapp.data.model.QuizResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * created by Ramanuj Kesharawani on 3/6/21
 */
interface ApiService {

    @POST("heritage/api/get_quiz")
    suspend fun getQuiz(@Query("key") key: String = "123456"): Response<QuizResponse>
    //@Body map: Map<String,Int> = mapOf("key" to 123456)
}