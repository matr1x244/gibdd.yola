package com.geekbrains.gibddyola.utils.flow_loading

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadingDataSource(
    private val loadingApi: LoadingApi,
    private val refreshIntervalMs: Long = 100L
) {
    fun get(): Flow<Char> {
        val loadingFlow: Flow<Char> = flow {
            val loadingText = loadingApi.getText()
            loadingText.forEach {
                emit(it)
                delay(refreshIntervalMs)
            }
        }
        return loadingFlow
    }
}