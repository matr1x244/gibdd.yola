package com.geekbrains.gibddyola.utils.updates

import java.util.*

class ReceiveUpdateDate {
    private val calendar: Calendar = Calendar.getInstance()

    fun get(): Boolean {
        return calendar.get(Calendar.DAY_OF_MONTH) < UpdateData.updateDay()
    }
}