package com.geekbrains.gibddyola

import androidx.lifecycle.ViewModel
import com.geekbrains.gibddyola.utils.checkConnection.ConnectionChecker
import com.geekbrains.gibddyola.utils.checkConnection.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val connectionChecker: ConnectionChecker) : ViewModel() {
    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus

    private val mainCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun checkConnection() {
        mainCoroutineScope.launch {
            connectionChecker.networkStatus.collect { status ->
                when (status) {
                    NetworkStatus.Available -> {
                        _connectionStatus.value = true
                    }
                    else -> {
                        _connectionStatus.value = false
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