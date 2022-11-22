package com.geekbrains.gibddyola.ui.stock.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class RotateUpPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val width = view.width
        val height = view.height
        val rotation = -5f * position * -1.25f

        view.pivotX = width * 0.5f
        view.pivotY = height * 0.1f
        view.rotation = rotation
    }
}