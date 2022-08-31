package com.geekbrains.gibddyola.utils.animation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.utils.audio_manager.AudioManager

class FragmentOpenBackStack {
    fun setClick(
        view: View,
        activity: FragmentActivity,
        fragment: Fragment,
        playSound: AudioManager
    ): Boolean {
        view.setOnClickListener {
            playSound.stopSoundAll()
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.to_left_in,
                    R.anim.to_left_out,
                    R.anim.to_right_in,
                    R.anim.to_right_out
                )
                .replace(R.id.main_activity_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        return false
    }
}