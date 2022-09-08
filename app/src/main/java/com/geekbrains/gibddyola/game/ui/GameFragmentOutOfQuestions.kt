package com.geekbrains.gibddyola.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.databinding.FragmentGameOutOfQuestionBinding

class GameFragmentOutOfQuestions : Fragment() {
    private var _binding: FragmentGameOutOfQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOutOfQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }
}