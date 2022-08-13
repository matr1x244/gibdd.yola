package com.geekbrains.gibddyola.utils

object ConvertCounts {

    fun convert(count: Int): String {
        var resultString: String
        if (count > 999) {
            resultString = "${count / 1000}"
            resultString += if ((count % 1000) >= 100) {
                ",${(count % 1000) / 100}K"
            } else {
                "K"
            }
        } else {
            resultString = count.toString()
        }
        return resultString
    }

}