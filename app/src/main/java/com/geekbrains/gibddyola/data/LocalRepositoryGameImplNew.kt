package com.geekbrains.gibddyola.data

import com.geekbrains.gibddyola.domain.game.QuestionNew
import com.geekbrains.gibddyola.domain.game.RepositoryGameNew

class LocalRepositoryGameImplNew : RepositoryGameNew{
    override fun getQuestions() = listOf(
        QuestionNew(
            1,
            "Вопрос 1",
            listOf(
                Pair("Ответ 1", false),
                Pair("Ответ 2", false),
                Pair("Ответ 3", true),
                Pair("Ответ 4", false)
            )
        ),
        QuestionNew(
            2,
            "Вопрос 2",
            listOf(
                Pair("Ответ 1", false),
                Pair("Ответ 2", false),
                Pair("Ответ 3", true),
                Pair("Ответ 4", false)
            )
        ),
    )
}