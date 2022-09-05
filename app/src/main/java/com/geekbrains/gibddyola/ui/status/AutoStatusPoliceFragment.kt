package com.geekbrains.gibddyola.ui.status

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusBinding
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusPoliceBinding
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.vkontakte.MyWebViewClient

class AutoStatusPoliceFragment : ViewBindingFragment<FragmentAutoStatusPoliceBinding>(
    FragmentAutoStatusPoliceBinding::inflate){

    companion object {
        fun newInstance() = AutoStatusPoliceFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebView()
    }

    private fun setWebView() {
        val url = "https://гибдд.рф/check/fines"
        val settings = binding.autoStatusWebView.settings
        settings.javaScriptEnabled = true
        settings.textZoom = 90

        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true
        }

        binding.autoStatusWebView.webViewClient = MyWebViewClient()
        binding.autoStatusWebView.setPadding(0, 0, 0, 0)
        binding.autoStatusWebView.loadUrl(url)

        when (requireActivity().resources.displayMetrics.densityDpi) {
            in (0..400) -> {
                binding.autoStatusWebView.scrollY = 870
            }
            in (400..500) -> {
                binding.autoStatusWebView.scrollY = 970
            }
            in (500..600) -> {
                binding.autoStatusWebView.scrollY = 1060
            }
        }
    }
}

