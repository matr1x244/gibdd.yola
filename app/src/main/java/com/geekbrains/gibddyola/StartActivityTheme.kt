package com.geekbrains.gibddyola

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class StartActivityTheme : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_theme)

        val version = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

        if (version) {
            val screen = installSplashScreen()
            screen.setOnExitAnimationListener { screenProvider ->
                ObjectAnimator.ofFloat(
                    screenProvider.view,
                    View.ALPHA, 5f, 0f
                ).apply {
                    duration = 900
                    doOnEnd {
                        screenProvider.remove()
                    }
                }.start()
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            ObjectAnimator.ofFloat(findViewById(R.id.iv_logo), View.ALPHA, 0.2f, 1.0f)
                .setDuration(900)
                .start()
            Handler(mainLooper).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 900L)
        }
    }
}