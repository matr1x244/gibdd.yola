package com.geekbrains.gibddyola.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.data.employee.LocalRepositoryImpl
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import kotlinx.coroutines.*

class MainViewModel(private val repository: LocalRepositoryImpl) : ViewModel() {

    private val _repos = MutableLiveData<List<EntityAvarkom>>()
    val repos: LiveData<List<EntityAvarkom>> = _repos

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun onShowListAvarkom() {
        coroutineScope.launch(Dispatchers.IO) {
            var result = repository.getListAvarkom()
            withContext(Dispatchers.Main) {
                _repos.postValue(result)
            }
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}