package com.geekbrains.gibddyola

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.di.koin.appModuleKoin
import com.geekbrains.gibddyola.di.koin.gameKoinModule
import com.geekbrains.gibddyola.di.koin.mainActivityKoinModule
import com.geekbrains.gibddyola.di.koin.vkNewsKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModuleKoin, vkNewsKoinModule, mainActivityKoinModule, gameKoinModule)
        }
    }
}

val Context.app: App
    get() = applicationContext as App

val Fragment.app: App
    get() = requireActivity().app

