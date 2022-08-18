package com.geekbrains.gibddyola.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Функционал View отображения SnackBack
 */
fun View.showSnackBarAction(
    text: String,
    actionText: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
    action: (View) -> Unit,)
{
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBarNoAction(
    text: Int,
    length: Int = Snackbar.LENGTH_SHORT)
{
    Snackbar.make(this, text, length).show()
}

fun View.showSnackBarTextNoAction(
    viewModel: VkNewsViewModel,
    viewLifecycleOwner: LifecycleOwner,
    length: Int = Snackbar.LENGTH_SHORT)
{
    var text = ""
    viewModel.flowData.observe(viewLifecycleOwner) {
        text += it
        Snackbar.make(this, text, length).show()
    }
}
