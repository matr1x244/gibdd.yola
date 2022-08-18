package com.geekbrains.gibddyola.utils.flow

import com.geekbrains.gibddyola.data.news.local.TooltipList

class TooltipApiImpl : TooltipApi {
    override suspend fun getTooltips(index: Int): CharArray {
        val testTooltip = TooltipList.getTooltip(index)
        return testTooltip.toCharArray()
    }
}