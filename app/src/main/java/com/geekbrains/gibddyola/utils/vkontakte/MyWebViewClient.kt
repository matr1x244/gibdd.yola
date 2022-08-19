package com.geekbrains.gibddyola.utils.vkontakte

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient(private val scrollPosition: Int) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        view?.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < scrollPosition) {
                view.scrollY = scrollPosition
            }
        }
        view?.scrollY = scrollPosition
    }
}