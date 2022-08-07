package com.geekbrains.gibddyola.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.data.LocalRepositoryImpl
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: LocalRepositoryImpl) : ViewModel() {

    private val _repos = MutableLiveData<List<EntityAvarkom>>()
    val repos: LiveData<List<EntityAvarkom>> = _repos

    fun onShowListAvarkom() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.getListAvarkom()
            withContext(Dispatchers.Main) {
                _repos.postValue(result)
            }
        }
    }
}