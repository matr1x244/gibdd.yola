package com.geekbrains.gibddyola.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameResultsQuestionsBinding
import com.geekbrains.gibddyola.game.data.QuestionRepositoryImpl


class ResultsQuestionsFragment : Fragment() {

    private var _binding: FragmentGameResultsQuestionsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ResultsQuestionsFragment()
    }

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

        // Set name on result page
        binding.textviewNameResult.text = "Результат тестирования ПДД"
        binding.textviewScore.text =
            //тут надо переписать логику получения кол-во правильных ответов + сколько было всего вопросов 20 50 100 и т.п
            "Количество правильных ответов: $score/${QuestionRepositoryImpl().getAllQuestions().size}"

//        binding.btnReloadGame.setOnClickListener {
//            // Reset the score when the game finishes
//            score = 0
//            // Go to homepage
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragment_main_container, GameFragment.newInstance())
//                ?.commitNow()
//        }

    }
}