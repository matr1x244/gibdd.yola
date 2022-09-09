package com.geekbrains.gibddyola.game.domain.entity

data class QuestionDomain(
    val id: String,
    val image: Int?,
    val question: String,
    val answers: List<Pair<String, Boolean>>,
    val answer_about: String
)