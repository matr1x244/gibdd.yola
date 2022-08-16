package com.geekbrains.gibddyola.game.domain.entity

sealed class AppState {
    data class Success(val questions: QuestionDomain) :AppState()
}