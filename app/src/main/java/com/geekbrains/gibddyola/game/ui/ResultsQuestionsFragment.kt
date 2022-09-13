package com.geekbrains.gibddyola.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameResultsQuestionsBinding

class ResultsQuestionsFragment : Fragment() {

    private var _binding: FragmentGameResultsQuestionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameResultsQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalTextViewBtn()
    }

    private fun finalTextViewBtn() {
        binding.textviewNameResult.text = "Результат тестирования ПДД"
        binding.textviewScore.text =
            "Количество правильных ответов: " +
                    "${arguments?.getInt("score") ?: 0}/" +
                    "${arguments?.getInt("numberOfQuestions")}"

        binding.btnReloadGame.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_activity_container,
                    GameFragment(-1)
                )
                .commitAllowingStateLoss()
        }
    }
}