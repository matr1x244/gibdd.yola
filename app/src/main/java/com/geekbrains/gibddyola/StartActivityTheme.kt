package com.geekbrains.gibddyola

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter

class StartActivityTheme : AppCompatActivity() {

    private lateinit var imgStart: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_theme)

        imgStart = findViewById(R.id.iv_logo)

//        val version = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//
//        if (version) {
//            val screen = installSplashScreen()
//            screen.setOnExitAnimationListener { screenProvider ->
//                ObjectAnimator.ofFloat(
//                    screenProvider.view,
//                    View.ALPHA, 5f, 0f
//                ).apply {
//                    duration = 900
//                    doOnEnd {
//                        screenProvider.remove()
//                    }
//                }.start()
//            }
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        } else {
            Glide.with(imgStart)
                .asGif()
                .load(R.mipmap.gif_logo_no_road)
                .transform(FitCenter(), CenterInside())
                .into(imgStart)
            ObjectAnimator.ofFloat(imgStart, View.TRANSLATION_X, -600.0f, 0.0f)
                .setDuration(700)
                .start()
            Handler(mainLooper).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 900L)
        }
    }
//}