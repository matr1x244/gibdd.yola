package com.geekbrains.gibddyola.domain.game

data class QuestionNew(
    val id: Int,
    val poster: Int?,
    val question: String,
    val answer: List<Pair<String, Boolean>>,
    val answer_about: String
)
