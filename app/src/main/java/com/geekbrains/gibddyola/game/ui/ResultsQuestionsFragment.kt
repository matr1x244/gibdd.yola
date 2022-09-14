package com.geekbrains.gibddyola.ui.game.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.LocalRepositoryGameImpl
import com.geekbrains.gibddyola.databinding.FragmentResultsQuestionsBinding


class ResultsQuestionsFragment : Fragment() {

    private var _binding: FragmentResultsQuestionsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ResultsQuestionsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set name on result page
        binding.textviewNameResult.text = getString(R.string.result_testing_pdd)
        binding.textviewScore.text = getString(
            R.string.result_score,
            score,
            LocalRepositoryGameImpl().getQuestions().size
        )

        binding.btnReloadGame.setOnClickListener {
            // Reset the score when the game finishes
            score = 0
            // Go to homepage
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(
                    R.id.container_questions_fragment,
                    QuestionsFragment.newInstance()
                )
                ?.commitNow()
        }

    }
}