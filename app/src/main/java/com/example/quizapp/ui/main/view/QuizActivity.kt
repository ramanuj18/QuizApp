package com.example.quizapp.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.ui.main.viewmodel.QuizViewModel
import com.example.quizapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = this
        binding.viewmodel = quizViewModel

        quizViewModel.fetchQuiz()

        observeQuiz()
    }

    private fun observeQuiz() {
        quizViewModel.quizResponse.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    enableDisableButtons(true)
                    quizViewModel.quizList()
                    quizViewModel.startTest(1)
                }
            }
        })

        quizViewModel.currentQuestion.observe(this, Observer {
            try {
                setDefaultBackground()
                enableDisableButtons(true)
                val counterText = "${quizViewModel.questionNumber}/${quizViewModel.totalQuestion}"
                binding.textViewQuizCounter.text = counterText
                binding.textViewQuestion.text = it.q
                val optionList = it.options
                binding.buttonOption1.text = optionList!![0]
                binding.buttonOption2.text = optionList[1]
                binding.buttonOption3.text = optionList[2]
                binding.buttonOption4.text = optionList[3]

            } catch (e: Exception) {
                e.printStackTrace()
            }

        })

        quizViewModel.quizCompleted.observe(this, Observer {
            if(it){
                openQuizResultActivity()
            }
        })
    }

    fun onOptionButtonClick(view: View, position: Int) {
        val cq = quizViewModel.currentQuestion.value
        cq?.let {
            if (it.correctIndex == position) {
                //correct answer
                quizViewModel.correctAnswer += 1
                changeBackgroundColor(position)
            } else {
                //incorrect answer
                quizViewModel.incorrectAnswer += 1
                changeBackgroundColor(it.correctIndex, position)
            }
        }
    }

    private fun setDefaultBackground() {
        binding.buttonOption1.setBackgroundColor(resources.getColor(R.color.purple_700))
        binding.buttonOption2.setBackgroundColor(resources.getColor(R.color.purple_700))
        binding.buttonOption3.setBackgroundColor(resources.getColor(R.color.purple_700))
        binding.buttonOption4.setBackgroundColor(resources.getColor(R.color.purple_700))
    }

    private fun enableDisableButtons(enable:Boolean){
        binding.buttonOption1.isEnabled = enable
        binding.buttonOption2.isEnabled = enable
        binding.buttonOption3.isEnabled = enable
        binding.buttonOption4.isEnabled = enable
    }

    private fun changeBackgroundColor(correctAnswerPosition: Int, incorrectAnswerPosition: Int? = null) {
        enableDisableButtons(false)
        when (correctAnswerPosition) {
            0 -> {
                binding.buttonOption1.setBackgroundColor(resources.getColor(R.color.green))
            }
            1 -> {
                binding.buttonOption2.setBackgroundColor(resources.getColor(R.color.green))
            }
            2 -> {
                binding.buttonOption3.setBackgroundColor(resources.getColor(R.color.green))
            }
            3 -> {
                binding.buttonOption4.setBackgroundColor(resources.getColor(R.color.green))
            }
        }

        incorrectAnswerPosition?.let { incorrect ->

            when (incorrect) {
                0 -> {
                    binding.buttonOption1.setBackgroundColor(resources.getColor(R.color.red))
                }
                1 -> {
                    binding.buttonOption2.setBackgroundColor(resources.getColor(R.color.red))
                }
                2 -> {
                    binding.buttonOption3.setBackgroundColor(resources.getColor(R.color.red))
                }
                3 -> {
                    binding.buttonOption4.setBackgroundColor(resources.getColor(R.color.red))
                }
            }
        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    quizViewModel.questionNumber += 1
                    quizViewModel.startTest(quizViewModel.questionNumber)
                }
            }

        }, 1000)
    }

    fun onSkipClick(view:View){
        quizViewModel.skippedQuestion += 1
        quizViewModel.questionNumber += 1
        quizViewModel.startTest(quizViewModel.questionNumber)
    }

    fun onQuitClick(view:View){
        openQuizResultActivity()
    }

    private fun openQuizResultActivity(){
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("totalQuestion",quizViewModel.totalQuestion)
        intent.putExtra("skippedQuestion",quizViewModel.skippedQuestion)
        intent.putExtra("correctAnswer",quizViewModel.correctAnswer)
        intent.putExtra("incorrectAnswer",quizViewModel.incorrectAnswer)
        intent.putExtra("attemptQuestion",quizViewModel.questionNumber)
        startActivity(intent)
        finish()
    }

}