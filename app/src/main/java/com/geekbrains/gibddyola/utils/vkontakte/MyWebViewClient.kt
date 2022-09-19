package com.geekbrains.gibddyola.utils.vkontakte

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.geekbrains.gibddyola.R

class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError
    ) {
        Toast.makeText(
            view?.context,
            R.string.no_internet_message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        val currentPosition = view?.scrollY!!
        view.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < currentPosition) {
                view.scrollY = currentPosition
            }
        }
    }
}