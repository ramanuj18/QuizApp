package com.example.quizapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repository.QuizRepository
import com.example.quizapp.util.NetworkHelper
import com.example.quizapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * created by Ramanuj Kesharawani on 5/6/21
 */
@HiltViewModel
class QuizViewModel @Inject constructor(
        private val quizRepository: QuizRepository,
        private val networkHelper: NetworkHelper
): ViewModel() {

    private val _quizResponse = MutableLiveData<Resource<List<Quiz>>>()
    val quizResponse: LiveData<Resource<List<Quiz>>>
        get() = _quizResponse

    private var quiz: List<Quiz>? = null

    var quizCompleted = MutableLiveData<Boolean>(false)

    var correctAnswer = 0
    var incorrectAnswer = 0
    var skippedQuestion = 0
    var questionNumber = 0
    var totalQuestion:Int? = 0

    var currentQuestion = MutableLiveData<Quiz>()

    fun quizList(){
        quiz = quizRepository.getAllQuiz()
        totalQuestion = quiz?.size
    }

     fun fetchQuiz() {
        viewModelScope.launch {
            _quizResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                quizRepository.getQuiz().let {
                    if (it.isSuccessful) {
                        it.body()?.result?.let { quiz ->
                            quizRepository.insertQuiz(quiz)
                        }
                        _quizResponse.postValue(Resource.success(null))
                    } else _quizResponse.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _quizResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    fun startTest(number: Int) {
        if(quiz.isNullOrEmpty()){
            return
        }

        if(quiz!!.size >= number){
            questionNumber = number
            currentQuestion.value = quiz!![number-1]
        }else{
            //show result
            quizCompleted.value = true
        }

    }

}