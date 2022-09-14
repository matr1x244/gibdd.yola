package com.geekbrains.gibddyola.game.domain

import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

interface QuestionRepository {
    fun getAllQuestions(): List<QuestionDomain>
}
