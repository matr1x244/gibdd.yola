package com.geekbrains.gibddyola.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.data.employee.LocalRepositoryImpl
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.utils.flow.FlowRepository
import com.geekbrains.gibddyola.utils.updates.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import java.io.File

class MainViewModel(
    private val repository: LocalRepositoryImpl,
    private val flowRepository: FlowRepository,
    private val downloadPath: String
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

    private val _downloadingProcess = MutableLiveData<Int>()
    val downloadingProcess: LiveData<Int> = _downloadingProcess

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
        val file = File("$downloadPath/${UpdateData.fileName()}")
        val url = UpdateData.apkUrl()
        coroutineScope.launch {
            downloadFile(file, url).collect {
                Log.e("", it.toString())
                when (it) {
                    is DownloadStatus.Success -> {
                        _downloadApkMessage.postValue(UpdateData.downloadSuccess())
                    }
                    is DownloadStatus.Error -> {
                        _downloadApkMessage.postValue(it.message)
                    }
                    is DownloadStatus.Progress -> {
                        _downloadingProcess.postValue(it.progress)
                    }
                }
            }
        }
    }

    fun checkUpdateDate() {
        val receiveUpdateDate = ReceiveUpdateDate()
        coroutineScope.launch {
            _isUpdateDate.postValue(receiveUpdateDate.get())
        }
    }

    fun deleteFile() {
        val deleter = ApkDelete(downloadPath)
        coroutineScope.launch {
            deleter.run()
        }
    }

    fun checkUpdateState(): Boolean {
        return downloadingProcess.value != null
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}