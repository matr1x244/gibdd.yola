package com.geekbrains.gibddyola.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.data.employee.LocalRepositoryImpl
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.utils.flow.FlowRepository
import com.geekbrains.gibddyola.utils.updates.ReceiveServerAppApk
import com.geekbrains.gibddyola.utils.updates.ReceiveServerAppData
import com.geekbrains.gibddyola.utils.updates.ReceiveUpdateDate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class MainViewModel(
    private val repository: LocalRepositoryImpl,
    private val flowRepository: FlowRepository
    ) : ViewModel() {

    private val _flowData = MutableLiveData<Char>()
    val flowData: LiveData<Char> = _flowData

    private val _repos = MutableLiveData<List<EntityAvarkom>>()
    val repos: LiveData<List<EntityAvarkom>> = _repos

    private val _appVersion = MutableLiveData<String>()
    val appVersion: LiveData<String> = _appVersion

    private val _downloadApkMessage = MutableLiveData<String>()
    val downloadApkMessage: LiveData<String> = _downloadApkMessage

    private val _isUpdateDate = MutableLiveData<Boolean>()
    val isUpdateDate: LiveData<Boolean> = _isUpdateDate

    private var tooltipIndex: Int = 0

    private var tooltipJob: Job? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getTooltip() {
        tooltipJob = viewModelScope.launch {
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

    fun stopGettingTooltip() {
        tooltipJob?.cancelChildren()
        tooltipJob = null
        _flowData.value = '\u0000'
    }

    fun setTooltipIndex(index: Int) {
        this.tooltipIndex = index
    }

    fun getServerVersion() {
        val versionReceiver = ReceiveServerAppData()
        coroutineScope.launch {
            val result = versionReceiver.getVersion()
            withContext(Dispatchers.Main) {
                _appVersion.postValue(result)
            }
        }
    }

    fun downloadNewAppApk() {
        val apkReceiver = ReceiveServerAppApk()
        coroutineScope.launch {
            _downloadApkMessage.postValue(apkReceiver.downloadFile())
        }
    }

    fun checkUpdateDate() {
        val receiveUpdateDate = ReceiveUpdateDate()
        coroutineScope.launch {
            _isUpdateDate.postValue(receiveUpdateDate.get())
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}