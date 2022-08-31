package com.geekbrains.gibddyola.utils.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import com.geekbrains.gibddyola.R

class MainMenuOpen {
    private val durationAnimOpenMenu = 300L

    fun setAnimation(view: View, isOpen: Boolean) {
        var startValue = 0F
        var stopValue = 0F
        var anim: Property<View, Float>? = null

        if (isOpen) {
            when (view.id) {
                R.id.fab_main_image -> {
                    startValue = 0F
                    stopValue = 450F
                    anim = View.ROTATION
                }
                R.id.option_one_container -> {
                    startValue = -50F
                    stopValue = -260F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_two_container -> {
                    startValue = -20F
                    stopValue = -130F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_three_container -> {
                    startValue = -80F
                    stopValue = -390F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_four_container -> {
                    startValue = -110F
                    stopValue = -520F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_five_container -> {
                    startValue = -140F
                    stopValue = -650F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_update_container -> {
                    startValue = -190F
                    stopValue = -850F
                    anim = View.TRANSLATION_Y
                }
                R.id.download_process_layout -> {
                    startValue = -490F
                    stopValue = -1150F
                    anim = View.TRANSLATION_Y
                }
            }

            ObjectAnimator.ofFloat(view, anim, startValue, stopValue)
                .setDuration(durationAnimOpenMenu)
                .start()

            view.animate()
                .alpha(0.8F)
                .setDuration(durationAnimOpenMenu * 2)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                    }
                })
            if (view.id == R.id.transparent_background) {
                view.animate()
                    .alpha(0.8F).duration = durationAnimOpenMenu
            }
        } else {
            when (view.id) {
                R.id.fab_main_image -> {
                    startValue = 450F
                    stopValue = 0F
                    anim = View.ROTATION
                }
                R.id.option_one_container -> {
                    startValue = -260F
                    stopValue = -50F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_two_container -> {
                    startValue = -130F
                    stopValue = -20F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_three_container -> {
                    startValue = -390F
                    stopValue = -80F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_four_container -> {
                    startValue = -520F
                    stopValue = -110F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_five_container -> {
                    startValue = -650F
                    stopValue = -140F
                    anim = View.TRANSLATION_Y
                }
                R.id.option_update_container -> {
                    startValue = -8500F
                    stopValue = -190F
                    anim = View.TRANSLATION_Y
                }
                R.id.download_process_layout -> {
                    startValue = -1150F
                    stopValue = -490F
                    anim = View.TRANSLATION_Y
                }
            }
            ObjectAnimator.ofFloat(view, anim, startValue, stopValue)
                .setDuration(durationAnimOpenMenu)
                .start()

            if (view.id != R.id.fab_main_image && view.id != R.id.transparent_background) {
                view.animate()
                    .alpha(0F)
                    .setDuration(durationAnimOpenMenu / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
            }
            if (view.id == R.id.transparent_background) {
                view.animate()
                    .alpha(0F).duration = durationAnimOpenMenu
            }
        }
    }
}