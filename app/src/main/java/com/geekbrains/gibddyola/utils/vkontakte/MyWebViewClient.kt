package com.geekbrains.gibddyola.utils.vkontakte

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.utils.showSnackBarNoAction
import com.google.android.material.snackbar.Snackbar

class MyWebViewClient(private val layout: FrameLayout? = null) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (layout != null) {
            layout.visibility = View.VISIBLE
            layout.isClickable = true
            layout.setOnClickListener {  }
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError
    ) {
        view?.showSnackBarNoAction(
            R.string.no_internet_message,
            Snackbar.LENGTH_SHORT
        )
        view?.visibility = View.INVISIBLE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        val currentPosition = view?.scrollY!!
        view.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < currentPosition) {
                view.scrollY = currentPosition
            }
        }
        if (layout != null) {
            layout.isClickable = false
            layout.visibility = View.GONE
        }
    }
}