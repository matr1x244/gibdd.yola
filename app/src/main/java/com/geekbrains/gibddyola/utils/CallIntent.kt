package com.geekbrains.gibddyola.utils

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity

object CallIntent {
    private const val number = "+7(8362)709-709"
    private val intent = Intent(Intent.ACTION_CALL)

    fun start(activity: FragmentActivity) {
        intent.data = Uri.parse("tel:$number")
        activity.startActivity(intent)
    }
}