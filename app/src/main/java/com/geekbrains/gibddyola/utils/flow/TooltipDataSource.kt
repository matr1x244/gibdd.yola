package com.geekbrains.gibddyola.utils.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TooltipDataSource(
    private val tooltipApi: TooltipApi,
    private val refreshIntervalMs: Long = 20
) {
    fun get(index: Int): Flow<Char> {
        val tooltipFlow: Flow<Char> = flow {
            val tooltip = tooltipApi.getTooltips(index)
            tooltip.forEach {
                emit(it)
                delay(refreshIntervalMs)
            }
        }
        return tooltipFlow
    }
}