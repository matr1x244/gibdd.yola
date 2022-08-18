package com.geekbrains.gibddyola.utils.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class FlowRepository(
    private val tooltipDataSource: TooltipDataSource
) {
    fun get(index: Int): Flow<Char> {
        return tooltipDataSource.get(index)
            .map { tooltip -> tooltip }
    }
}