package com.geekbrains.gibddyola

import com.geekbrains.gibddyola.data.LocalRepositoryGameImpl
import com.geekbrains.gibddyola.data.employee.LocalRepositoryImpl
import com.geekbrains.gibddyola.ui.main.MainViewModel
import com.geekbrains.gibddyola.utils.flow.FlowRepository
import com.geekbrains.gibddyola.utils.flow.TooltipApi
import com.geekbrains.gibddyola.utils.flow.TooltipApiImpl
import com.geekbrains.gibddyola.utils.flow.TooltipDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModuleKoin = module {

    factory { LocalRepositoryImpl() }
    factory { LocalRepositoryGameImpl() }

    single<TooltipApi>(named("tooltipApi")) { TooltipApiImpl() }

    single(named("tooltipDataSource")) { TooltipDataSource(get(named("tooltipApi"))) }

    single(named("flowRepository")) { FlowRepository(get(named("tooltipDataSource"))) }

    viewModel { MainViewModel(get(), get(named("flowRepository"))) }
}