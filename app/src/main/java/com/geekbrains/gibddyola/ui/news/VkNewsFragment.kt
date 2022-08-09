package com.geekbrains.gibddyola.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentVkNewsBinding

class VkNewsFragment : Fragment() {

    private var _binding: FragmentVkNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVkNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = VkNewsFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}