package com.geekbrains.gibddyola.game.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.game.domain.QuestionDatabaseHelper
import com.geekbrains.gibddyola.game.domain.entity.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class GameViewModel(
    private val dbHelper: QuestionDatabaseHelper,
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val scoreToObserve: MutableLiveData<Int> = MutableLiveData(),
    private val numberOfQuestions: MutableLiveData<Int> = MutableLiveData(),
    private val listAnsweredQuestions: MutableLiveData<Int> = MutableLiveData(),
    private val listAnsweredQuestions2: MutableLiveData<MutableSet<Int>> = MutableLiveData(),
//    private val numberOfQuestions: MutableLiveData<Int> = MutableLiveData()
) : ViewModel() {
    var listOfAnsweredQuestions = mutableSetOf<Int>()

    //    var numberOfQuestions = mutableListOf<Int>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val listAnsweredQuestion = mutableSetOf<Int>()
    fun getLiveData() = liveDataToObserve
    fun getScore() = scoreToObserve
    fun getNumberOfQuestion() = numberOfQuestions
    fun setNumberOfQuestion(number: Int) {
        numberOfQuestions.value = number
    }
    private var size = 0

    init {
        coroutineScope.launch {
            size = dbHelper.getAll().size
        }
    }

    fun setScore(score: Int) {
        scoreToObserve.value = score
    }

    fun addAnsweredQuestion(id: Int) {
        listAnsweredQuestions2.value?.add(id)
    }

    fun addAnsweredQuestion2(id: Int) {
        listAnsweredQuestions.value = id
    }

    fun getAnsweredQuestions() = listAnsweredQuestions


    fun getListAnsweredQuestion(): List<Int> {
        return listAnsweredQuestions2.value?.toList() ?: listOf()
    }

    fun getQuestion(numberOfQuestion: Int) {
        viewModelScope.launch {
            try {
                liveDataToObserve.postValue(AppState.Success(dbHelper.getAll()[numberOfQuestion]))
            } catch (e: Exception) {
                Log.e("", e.message.toString())
            }

        }
    }

    fun getQuestionCount(): Int {
        return size
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}