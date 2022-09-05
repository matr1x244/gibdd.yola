package com.geekbrains.gibddyola

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StartActivityTheme : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_theme)

        /*Анимация загрузки*/
        ObjectAnimator.ofFloat(findViewById(R.id.container_start_theme), View.ALPHA, 1.0f, 0.0f)
            .setDuration(900)
            .start()

        /*Отложенный старт MainActivity*/
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 750L)
    }
}