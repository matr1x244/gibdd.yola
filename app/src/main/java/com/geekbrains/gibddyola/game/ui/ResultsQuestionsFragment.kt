package com.geekbrains.gibddyola.ui.game.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameResultsQuestionsBinding
import com.geekbrains.gibddyola.databinding.FragmentMainBinding
import com.geekbrains.gibddyola.game.ui.GameFragment
import com.geekbrains.gibddyola.utils.ViewBindingFragment


class ResultsQuestionsFragment : ViewBindingFragment<FragmentGameResultsQuestionsBinding>(FragmentGameResultsQuestionsBinding::inflate) {

    companion object {
        fun newInstance() = ResultsQuestionsFragment()
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