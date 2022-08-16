package com.geekbrains.gibddyola.game.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.game.domain.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),

    private val questionRepositoryImpl : QuestionRepository = QuestionRepositoryImpl()
): ViewModel() {
    fun getQuestions() = with(viewModelScope) {
        launch (Dispatchers.IO){
            liveDataToObserve.postValue(AppState.Success(questionRepositoryImpl.getAllQuestions()))
        }
    }
}