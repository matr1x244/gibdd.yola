package com.geekbrains.gibddyola.domain.game

interface RepositoryGame {
    fun getQuestions(): ArrayList<Question>
}