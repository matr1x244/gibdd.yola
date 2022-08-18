package com.geekbrains.gibddyola.utils.flow

interface TooltipApi {
    suspend fun getTooltips(index: Int): CharArray
}