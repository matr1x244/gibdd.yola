package com.geekbrains.gibddyola.utils.audio_manager

import android.content.Context
import android.media.MediaPlayer
import com.geekbrains.gibddyola.R

class AudioManager(private val context: Context) : AudioManagerInput {

    private var mediaPlayer: MediaPlayer? = null

    override fun startSoundUpDate() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_update_app)
        mediaPlayer?.start()
    }

    override fun stopSoundUpDate() {
        mediaPlayer?.stop()
    }

    override fun exitSoundApp() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_exit_app)
        mediaPlayer?.start()
    }


}