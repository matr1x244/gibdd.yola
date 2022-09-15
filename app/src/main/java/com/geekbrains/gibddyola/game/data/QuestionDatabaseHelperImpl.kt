package com.geekbrains.gibddyola.game.data

import com.geekbrains.gibddyola.game.domain.QuestionDatabase
import com.geekbrains.gibddyola.game.domain.QuestionDatabaseHelper
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

class QuestionDatabaseHelperImpl(private val questionDatabase: QuestionDatabase) :
    QuestionDatabaseHelper {
    override suspend fun getAll(): List<QuestionDomain> {
        return questionDatabase.questionDao().getAll()
    }

    override suspend fun get(id: String): QuestionDomain {
        return questionDatabase.questionDao().get(id)
    }

    override suspend fun insertAll(questions: List<QuestionDomain>) {
        questionDatabase.questionDao().insertAll(questions)
    }

    override suspend fun insert(question: QuestionDomain) {
        questionDatabase.questionDao().insert(question)
    }
}