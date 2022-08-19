package com.geekbrains.gibddyola.utils.vkontakte

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
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