package com.geekbrains.gibddyola.domain.game

data class Question(
    val id: Int,
    val text: String,
    val image: Int?,
    val answer1: String,
    val answer2: String,
    val answer3: String?,
    val answer4: String?,
    val correctAnswer: String
)