package com.geekbrains.gibddyola

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.ui.about.AboutFragment
import com.geekbrains.gibddyola.ui.main.MainFragment

class MainActivity : AppCompatActivity(), ControllerOpenFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            startConteinerAlphaAnimator()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun startConteinerAlphaAnimator() {
        ObjectAnimator.ofFloat(findViewById(R.id.main_activity_container), View.ALPHA, 0.2f, 1.0f)
            .setDuration(600)
            .start()
    }

    override fun aboutFragment(localClick: EntityAvarkom) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            )
            .replace(R.id.main_activity_container, AboutFragment.newInstance(localClick))
            .addToBackStack("")
            .commit()
    }
}