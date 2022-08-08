package com.geekbrains.gibddyola.domain.game

data class QuestionNew(
    val id: Int,
    val question: String,
    val answer: List<Pair<String, Boolean>>
)
