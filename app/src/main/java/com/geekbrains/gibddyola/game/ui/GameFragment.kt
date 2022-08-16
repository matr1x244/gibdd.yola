package com.geekbrains.gibddyola.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameBinding
import com.geekbrains.gibddyola.game.domain.entity.AppState
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var isCorrectAnswer: Boolean = false
    private var isCheckedAnswer = false
    private var checkedAnswer = -1

    private val gameAdapter: GameFragmentAdapter by lazy {
        GameFragmentAdapter(object :
            OnItemViewClickListener {
            override fun onItemViewClick(question: QuestionDomain, position: Int) {
                with(binding) {
                    btnCheck.visibility = View.VISIBLE
                    isCorrectAnswer = question.answers[position].second
                    btnCheck.setOnClickListener {
                        checkedAnswer = position
                        btnCheck.visibility = View.GONE
                        btnNext.visibility = View.VISIBLE
                        tvAnswerComment.text = question.answer_about
                        tvAnswerComment.visibility = View.VISIBLE
                        isCheckedAnswer = true

                        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

                        btnNext.setOnClickListener {
                            activity!!.supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_activity_container, GameFragment())
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }
            }
        }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.getQuestion(0)
            rvAnswers.layoutManager =
                LinearLayoutManager(requireContext())
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        }
    }

    private fun renderData(appState: AppState?) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    tvQuestion.text = appState.questions.question
                    appState.questions.image?.let { ivImageQuestion.setBackgroundResource(it) }
                    gameAdapter.setData(appState.questions, isCheckedAnswer, checkedAnswer)
                    rvAnswers.adapter = gameAdapter
                }
            }
            else -> {}
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(question: QuestionDomain, position: Int)
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}