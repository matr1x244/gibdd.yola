package com.geekbrains.gibddyola.utils.flow_loading

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoadingFlowRepository(
    private val loadingDataSource: LoadingDataSource
) {
    fun get(): Flow<Char> {
        return loadingDataSource.get()
            .map { loadingText -> loadingText }
    }
}