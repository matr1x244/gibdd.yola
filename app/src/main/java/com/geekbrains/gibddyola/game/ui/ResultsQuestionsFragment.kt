package com.geekbrains.gibddyola.game.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameResultsQuestionsBinding
import com.geekbrains.gibddyola.utils.ViewBindingFragment


class ResultsQuestionsFragment : ViewBindingFragment<FragmentGameResultsQuestionsBinding>(FragmentGameResultsQuestionsBinding::inflate) {

    companion object {
        fun newInstance() = ResultsQuestionsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalTextViewBtn()
    }

        @SuppressLint("SetTextI18n")
        private fun finalTextViewBtn() {
            binding.textviewNameResult.text = getString(R.string.result_test_pdd)
            binding.textviewScore.text =
                getString(R.string.number_correct_answers) +
                        " ${arguments?.getInt("score") ?: 0}/" +
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