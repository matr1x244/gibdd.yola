package com.geekbrains.gibddyola

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.gibddyola.utils.checkConnection.ConnectionChecker
import com.geekbrains.gibddyola.utils.checkConnection.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivityViewModel(private val connectionChecker: ConnectionChecker) : ViewModel() {
    private val _connectionStatus = MutableLiveData<Boolean>()
    val connectionStatus: LiveData<Boolean> = _connectionStatus

    private val mainCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun checkConnection() {
        mainCoroutineScope.launch {
            connectionChecker.networkStatus.collect { status ->
                when (status) {
                    NetworkStatus.Available -> {
                        _connectionStatus.postValue(true)
                    }
                    else -> {
                        _connectionStatus.postValue(false)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        mainCoroutineScope.cancel()
        super.onCleared()
    }

}