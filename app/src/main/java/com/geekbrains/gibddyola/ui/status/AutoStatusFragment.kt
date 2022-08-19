package com.geekbrains.gibddyola.ui.status

import android.content.Context
import android.graphics.Picture
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.*
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusBinding
import com.geekbrains.gibddyola.utils.vkontakte.MyWebViewClient
import kotlin.math.round

class AutoStatusFragment : Fragment() {

    private var _binding: FragmentAutoStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWebView()
    }

    private fun setWebView() {
        val url = "https://гибдд.рф/check/auto%20йошкар-ола"
        val settings = binding.autoStatusWebView.settings
        settings.javaScriptEnabled = true
        settings.textZoom = 130

        binding.autoStatusWebView.webViewClient = MyWebViewClient()
        binding.autoStatusWebView.setPadding(0, 0, 0, 0)
        binding.autoStatusWebView.loadUrl(url)
        when(requireActivity().resources.displayMetrics.densityDpi) {
            in (0..400) -> {
                binding.autoStatusWebView.scrollY = 820
            }
            in(400..500) -> {
                binding.autoStatusWebView.scrollY = 920
            }
            in(500..600) -> {
                binding.autoStatusWebView.scrollY = 1020
            }
        }
    }

    companion object {
        fun newInstance() = AutoStatusFragment()
    }
}


