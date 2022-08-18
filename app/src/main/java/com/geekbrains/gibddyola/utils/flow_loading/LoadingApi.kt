package com.geekbrains.gibddyola.utils.flow_loading

interface LoadingApi {
    suspend fun getText(): CharArray
}