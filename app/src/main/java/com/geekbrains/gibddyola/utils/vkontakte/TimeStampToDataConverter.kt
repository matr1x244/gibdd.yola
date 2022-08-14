package com.geekbrains.gibddyola.utils.vkontakte

import java.text.SimpleDateFormat
import java.util.*

object TimeStampToDataConverter {
    fun convert(dateTime: Int): String {
        val cal = Calendar.getInstance()
        val tz = cal.timeZone
        val sdf = SimpleDateFormat("dd/MM/YYYY")
        sdf.timeZone = tz
        return sdf.format(dateTime.toLong() * 1000)
    }
}