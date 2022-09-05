package com.geekbrains.gibddyola.game.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.game.data.QuestionRepositoryImpl
import com.geekbrains.gibddyola.game.domain.QuestionRepository
import com.geekbrains.gibddyola.game.domain.entity.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val questionRepositoryImpl: QuestionRepository = QuestionRepositoryImpl(),
    private val scoreToObserve: MutableLiveData<Int> = MutableLiveData(),
    private val listAnsweredQuestions: MutableLiveData<Int> = MutableLiveData()
) : ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getScore() = scoreToObserve
    fun setScore(score: Int) {
        scoreToObserve.value = score
    }
    fun addAnsweredQuestion(id: Int) {
        listAnsweredQuestions.value = id
    }
    fun getAnsweredQuestions() = listAnsweredQuestions

    fun getQuestion(numberOfQuestion: Int) = with(viewModelScope) {
        launch(Dispatchers.IO) {
            liveDataToObserve.postValue(AppState.Success(questionRepositoryImpl.getAllQuestions()[numberOfQuestion]))
        }
    }

    fun getQuestionCount(): Int = questionRepositoryImpl.getAllQuestions().size
}