package com.geekbrains.gibddyola.utils

import android.view.View
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
    text: String,
    length: Int = Snackbar.LENGTH_SHORT)
{
    Snackbar.make(this, text, length).show()
}
