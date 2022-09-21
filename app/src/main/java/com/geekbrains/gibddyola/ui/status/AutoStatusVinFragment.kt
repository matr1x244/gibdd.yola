package com.geekbrains.gibddyola.ui.status

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusVinBinding
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.vkontakte.MyWebViewClient

class AutoStatusVinFragment : ViewBindingFragment<FragmentAutoStatusVinBinding>(
    FragmentAutoStatusVinBinding::inflate
) {

    companion object {
        fun newInstance() = AutoStatusVinFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebView()
    }

    private fun setWebView() {
        val url = getString(R.string.ocago_status_check_url)
        val settings = binding.autoStatusWebView.settings
        settings.textZoom = 90

        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true
        }

        binding.autoStatusWebView.webViewClient = MyWebViewClient(binding.loadingProcessLayout)
        binding.autoStatusWebView.setPadding(0, 0, 0, 0)
        binding.autoStatusWebView.loadUrl(url)
        ObjectAnimator.ofFloat(binding.containerAutoStatusVin, View.TRANSLATION_Y, -200F, 0F)
            .setDuration(1900L).start()
    }

}