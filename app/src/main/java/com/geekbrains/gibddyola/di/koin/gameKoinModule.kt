package com.geekbrains.gibddyola.di.koin

import com.geekbrains.gibddyola.game.data.QuestionDatabaseHelperImpl
import com.geekbrains.gibddyola.game.domain.QuestionDatabase
import com.geekbrains.gibddyola.game.domain.QuestionDatabaseBuilder
import com.geekbrains.gibddyola.game.domain.QuestionDatabaseHelper
import com.geekbrains.gibddyola.game.ui.GameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val gameKoinModule = module {
    single<QuestionDatabase>(named("database")) {
        QuestionDatabaseBuilder.getInstance(androidContext())
    }
    single<QuestionDatabaseHelper>(named("db_helper")) {
        QuestionDatabaseHelperImpl(get(named("database")))
    }
    viewModel(named("game_view_model")) {
        GameViewModel(get(named("db_helper")))
    }
}