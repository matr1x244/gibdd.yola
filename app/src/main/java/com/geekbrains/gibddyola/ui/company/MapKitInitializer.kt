package com.geekbrains.gibddyola.ui.company

import android.content.Context
import com.yandex.mapkit.MapKitFactory

object MapKitInitializer {
    /**
     * Если не делать такую иницилизацию,
     * тогда если заходить во фрагмент, потом выходить и опять заходить будет ->
     * java.lang.AssertionError: setApiKey() should be called before initialize()!
     */

    private var initialized = false

    fun initialize(apikey: String, context: Context) {
        if (initialized) {
            return
        }

        MapKitFactory.setApiKey(API_KEY)
        MapKitFactory.initialize(context)
        initialized = true
    }
}