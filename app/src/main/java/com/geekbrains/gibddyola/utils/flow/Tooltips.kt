package com.geekbrains.gibddyola.utils.flow

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.local.TooltipList
import com.geekbrains.gibddyola.ui.main.MainViewModel

class Tooltips {
    private val tooltipSize = TooltipList.getTooltipSize()
    private var toolTipChars = ""
    private var currentTooltipNumber = 0
    fun set(
        prefs: SharedPreferences,
        view: TextView,
        tooltipNumber: String,
        viewModel: MainViewModel,
        lifecycleOwner: LifecycleOwner,
        context: Context
    ) {
        if (prefs.contains(tooltipNumber)) {
            currentTooltipNumber = prefs.getInt(tooltipNumber, 0)
        } else {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(tooltipNumber, 0)
            editor.apply()
        }
        if (currentTooltipNumber < tooltipSize - 1) {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(tooltipNumber, currentTooltipNumber + 1)
            editor.apply()
        } else {
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putInt(tooltipNumber, 0)
            editor.apply()
        }

        viewModel.setTooltipIndex(currentTooltipNumber)
        viewModel.getTooltip()

        viewModel.flowData.observe(lifecycleOwner) { tooltipChar ->
            toolTipChars += tooltipChar

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val spannableStringBuilder = SpannableStringBuilder(toolTipChars)
                spannableStringBuilder.setSpan(
                    BulletSpan(
                        10,
                        ContextCompat.getColor(context, R.color.light_green_600),
                        10
                    ), 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                view.text = spannableStringBuilder
            } else {
                view.text = toolTipChars
            }
        }
    }
}