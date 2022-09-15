package com.geekbrains.gibddyola.game.domain.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnswersConverter {
    @TypeConverter
    fun fromAnswers(answers: List<Pair<String, Boolean>>): String {
        val gson = Gson()
        return gson.toJson(answers)
    }

    @TypeConverter
    fun toAnswers(value: String): List<Pair<String, Boolean>> {
        val listType = object : TypeToken<List<Pair<String, Boolean>>>() {}.type
        return Gson().fromJson(value, listType)
    }
}