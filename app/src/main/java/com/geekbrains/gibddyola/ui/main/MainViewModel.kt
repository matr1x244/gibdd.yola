package com.geekbrains.gibddyola.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.data.employee.LocalRepositoryImpl
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.utils.flow.FlowRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn

class MainViewModel(
    private val repository: LocalRepositoryImpl,
    private val flowRepository: FlowRepository
    ) : ViewModel() {

    private val _flowData = MutableLiveData<Char>()
    val flowData: LiveData<Char> = _flowData

    private val _repos = MutableLiveData<List<EntityAvarkom>>()
    val repos: LiveData<List<EntityAvarkom>> = _repos

    private var tooltipIndex: Int = 0

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getTooltip() {
        viewModelScope.launch {
            flowRepository.get(tooltipIndex).collectLatest { tooltipChars ->
                _flowData.postValue(tooltipChars)
            }
            _flowData.value = '\u0000'
        }
    }

    fun onShowListAvarkom() {
        coroutineScope.launch {
            val result = repository.getListAvarkom()
            withContext(Dispatchers.Main) {
                _repos.postValue(result)
            }
        }
    }

    fun setTooltipIndex(index: Int) {
        this.tooltipIndex = index
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}