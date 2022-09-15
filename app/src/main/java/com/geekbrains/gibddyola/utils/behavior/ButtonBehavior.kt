package com.geekbrains.gibddyola.utils.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonBehavior(context: Context, attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        if (dependency is AppBarLayout) {
            val bar = dependency //связываем с appbar
            val barHeight = bar.height.toFloat()
            val barY = bar.y

            /*Скрываем кнопку по условию*/
            if (abs(barY) > (barHeight * 3 / 3)) {
                child.visibility = View.GONE
            } else {
                child.visibility = View.VISIBLE
                child.alpha = ((barHeight * 1 / 3) - abs(barY / 2)) / (barHeight * 1 / 3) //плавное исчезание
            }
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}