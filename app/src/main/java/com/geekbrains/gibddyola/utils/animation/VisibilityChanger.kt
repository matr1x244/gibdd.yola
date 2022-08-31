package com.geekbrains.gibddyola.utils.animation

import android.view.View

class VisibilityChanger() {
    fun change(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}