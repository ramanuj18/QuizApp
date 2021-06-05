package com.example.quizapp.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizResultBinding

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz_result)
        binding.handler = this

        val totalQuestion = intent.getIntExtra("totalQuestion",0)
        val skippedQuestion = intent.getIntExtra("skippedQuestion",0)
        val correctAnswer = intent.getIntExtra("correctAnswer",0)
        val incorrectAnswer = intent.getIntExtra("incorrectAnswer",0)
        val attemptQuestion = intent.getIntExtra("attemptQuestion",0) -1


        val answeredString = "You Answered $attemptQuestion of the $totalQuestion questions"
        val correctAnswerString = "Correct Answer      $correctAnswer"
        val incorrectAnswerString = "Incorrect Answer    $incorrectAnswer"
        val skippedQuestionString = "Skipped Question    $skippedQuestion"

        binding.textViewAnsweredQuestion.text = answeredString
        binding.textViewCorrect.text = correctAnswerString
        binding.textViewSkipped.text = skippedQuestionString
        binding.textViewIncorrect.text = incorrectAnswerString
    }

    fun playAgainClick(view: View){
        val intent = Intent(this, QuizActivity::class.java)
        startActivity(intent)
        finish()
    }
}