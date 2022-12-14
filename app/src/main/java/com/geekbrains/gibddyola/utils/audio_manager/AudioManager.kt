package com.geekbrains.gibddyola.utils.audio_manager

import android.content.Context
import android.media.MediaPlayer
import com.geekbrains.gibddyola.R

class AudioManager(private val context: Context) : AudioManagerInput {

    private var mediaPlayer: MediaPlayer? = null

    override fun startSoundUpDate() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_update_app)
        mediaPlayer?.setVolume(0.2f,0.2f)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    override fun exitStartSoundApp() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_exit_app)
        mediaPlayer?.setVolume(0.2f, 0.2f)
        mediaPlayer?.start()
    }

    override fun pauseSoundAll() {
        mediaPlayer?.pause()
    }

    override fun stopSoundAll() {
        mediaPlayer?.stop()
    }

}