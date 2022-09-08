package com.geekbrains.gibddyola.di.koin

import com.geekbrains.gibddyola.MainActivity
import com.geekbrains.gibddyola.MainActivityViewModel
import com.geekbrains.gibddyola.utils.checkConnection.ConnectionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainActivityKoinModule = module {

    scope<MainActivity> {

        scoped(named("connection_checker")) { ConnectionChecker(androidContext()) }

        viewModel(named("mainActivityViewModel")) {
            MainActivityViewModel(get(named("connection_checker")))
        }
    }
}