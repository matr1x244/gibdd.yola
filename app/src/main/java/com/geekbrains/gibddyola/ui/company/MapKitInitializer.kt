package com.geekbrains.gibddyola.ui.company

import android.content.Context
import com.geekbrains.gibddyola.BuildConfig
import com.yandex.mapkit.MapKitFactory

object MapKitInitializer {

    private var initialized = false

    fun initialize(context: Context) {
        if (initialized) {
            return
        }

        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAP_API)
        MapKitFactory.initialize(context)
        initialized = true
    }
}