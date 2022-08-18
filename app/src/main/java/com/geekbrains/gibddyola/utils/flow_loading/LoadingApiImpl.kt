package com.geekbrains.gibddyola.utils.flow_loading

import com.geekbrains.gibddyola.R

class LoadingApiImpl : LoadingApi {
    private val loadingText: String = "Загружаем........."
    override suspend fun getText(): CharArray {
        return loadingText.toCharArray()
    }
}