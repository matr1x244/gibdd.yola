package com.geekbrains.gibddyola.game.domain

interface QuestionRepository {
    fun getAllQuestions(): List<String>
}
