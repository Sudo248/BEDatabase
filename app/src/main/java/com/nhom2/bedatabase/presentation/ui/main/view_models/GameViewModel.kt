package com.nhom2.bedatabase.presentation.ui.main.view_models

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.domain.repository.MainRepository
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private var _randomQuestions: MutableLiveData<List<Eng>> = MutableLiveData()
    val randomQuestions: LiveData<List<Eng>> = _randomQuestions

    private var _isCorrectAnswer: MutableLiveData<Int> = MutableLiveData(0)
    val isCorrectAnswer: LiveData<Int> = _isCorrectAnswer

    var listEngWords: List<Eng> = listOf()

    var answerIndex = 0
    init{
//        getRandomWord(0)
    }
    fun getRandomWord(time: Long = 2000){
        viewModelScope.launch(Dispatchers.IO){
            delay(time)
            _isCorrectAnswer.postValue(0)
            answerIndex = (0..3).random()
            val randomWords = mutableListOf<Eng>()
            val tempListWords = listEngWords.toMutableList()
            repeat(4){
                val engWord = tempListWords.random()
                tempListWords.remove(engWord)
                randomWords.add(engWord)
            }
            randomWords.forEach{
                Log.e("questions", it.content)
            }

            _randomQuestions.postValue(randomWords)
        }
    }
    fun checkAnswer(submitAnswer: Int){
        if (answerIndex == submitAnswer){
            _isCorrectAnswer.postValue(1)
        } else {
            _isCorrectAnswer.postValue(2)
        }
    }
}