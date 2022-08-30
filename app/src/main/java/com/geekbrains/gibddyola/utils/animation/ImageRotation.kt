package com.geekbrains.gibddyola.utils.animation

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView

class ImageRotation(val image: ImageView) {
    private val animator: ObjectAnimator =
        ObjectAnimator.ofFloat(image, View.ROTATION, 0f, 360f)

    fun startAnimation() {
        animator.repeatCount = Animation.INFINITE
        animator.duration = 1000L
        animator.start()
    }

    fun stopAnimation() {
        animator.cancel()
    }
}