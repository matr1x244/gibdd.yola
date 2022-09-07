package com.geekbrains.gibddyola.ui.status

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusPoliceBinding
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.vkontakte.MyWebViewClient

class AutoStatusPoliceFragment : ViewBindingFragment<FragmentAutoStatusPoliceBinding>(
    FragmentAutoStatusPoliceBinding::inflate
) {

    companion object {
        fun newInstance() = AutoStatusPoliceFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebView()
    }

    private fun setWebView() {
        val url = getString(R.string.police_status_check_url)
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

        binding.autoStatusWebView.webViewClient = MyWebViewClient()
        binding.autoStatusWebView.setPadding(0, 0, 0, 0)
        binding.autoStatusWebView.loadUrl(url)

        when (requireActivity().resources.displayMetrics.densityDpi) {
            in (getInteger(R.integer.low_density_start)..
                    getInteger(R.integer.low_density_end)) -> {
                binding.autoStatusWebView.scrollY =
                    getInteger(R.integer.low_density_y_scroll)
            }
            in (getInteger(R.integer.middle_density_start)..
                    getInteger(R.integer.middle_density_end)) -> {
                binding.autoStatusWebView.scrollY =
                    getInteger(R.integer.middle_density_y_scroll)
            }
            in (getInteger(R.integer.high_density_start)..
                    getInteger(R.integer.high_density_end)) -> {
                binding.autoStatusWebView.scrollY =
                    getInteger(R.integer.high_density_y_scroll)
            }
        }
    }

    private fun getInteger(id: Int): Int {
        return resources.getInteger(id)
    }
}

