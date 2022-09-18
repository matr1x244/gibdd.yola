package com.geekbrains.gibddyola.game.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameResultsQuestionsBinding
import com.geekbrains.gibddyola.ui.main.MainFragment
import com.geekbrains.gibddyola.utils.ViewBindingFragment

class ResultsQuestionsFragment :
    ViewBindingFragment<FragmentGameResultsQuestionsBinding>(FragmentGameResultsQuestionsBinding::inflate) {

    companion object {
        fun newInstance() = ResultsQuestionsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalTextViewBtn()
        btnExitGame()
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

    private fun btnExitGame() {
        binding.btnExitGame.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.to_left_in,
                    R.anim.to_left_out,
                    R.anim.to_right_in,
                    R.anim.to_right_out
                )
                .replace(R.id.main_activity_container, MainFragment.newInstance())
                .commit()
        }
    }

}