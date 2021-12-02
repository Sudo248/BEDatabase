package com.nhom2.bedatabase.presentation.ui.main.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private var _randomQuestions: MutableLiveData<ArrayList<Eng>> = MutableLiveData()
    val randomQuestions: LiveData<ArrayList<Eng>> = _randomQuestions

    private var _isCorrectAnswer: MutableLiveData<Int> = MutableLiveData(0)
    val isCorrectAnswer: LiveData<Int> = _isCorrectAnswer

    val listEngWords: ArrayList<Eng> = arrayListOf()

    var answerIndex = 0
    init {

    }

    fun getRandomWord(){
        viewModelScope.launch(Dispatchers.IO){
            delay(2000)
            _isCorrectAnswer.postValue(0)
            answerIndex = (0..4).random()
            val randomWords = arrayListOf<Eng>()
            for (i in (0..4)){
                var engWord = listEngWords.random()
                while (!randomWords.contains(engWord)){
                    engWord = listEngWords.random()
                }
                randomWords.add(engWord)
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