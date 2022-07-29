package com.geekbrains.gibddyola

import com.geekbrains.gibddyola.data.LocalRepositoryImpl
import com.geekbrains.gibddyola.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModuleKoin = module {

    factory { LocalRepositoryImpl() }

    viewModel { MainViewModel(get()) }
}