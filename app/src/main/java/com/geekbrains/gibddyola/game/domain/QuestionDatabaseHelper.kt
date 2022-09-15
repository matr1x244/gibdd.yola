package com.geekbrains.gibddyola.game.domain

import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

interface QuestionDatabaseHelper {
    suspend fun getAll(): List<QuestionDomain>
    suspend fun get(id: String): QuestionDomain
    suspend fun insertAll(questions: List<QuestionDomain>)
    suspend fun insert(question: QuestionDomain)
}