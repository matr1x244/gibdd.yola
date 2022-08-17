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
import com.geekbrains.gibddyola.ui.game.test.score

class GameFragment(private var score: Int) : Fragment() {
    private val viewModel: GameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var chooseAnswer = -1

    private val gameAdapter: GameFragmentAdapter by lazy {
        GameFragmentAdapter(object :
            OnItemViewClickListener {
            override fun onItemViewClick(question: QuestionDomain, position: Int) {
                with(binding) {
                    btnCheck.visibility = View.VISIBLE
                    btnCheck.setOnClickListener {
                        chooseAnswer = position
                        btnCheck.visibility = View.GONE
                        btnNext.visibility = View.VISIBLE
                        tvAnswerComment.text = question.answer_about
                        tvAnswerComment.visibility = View.VISIBLE

                        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

                        if (question.answers[position].second) score++
                        tvScore.text = score.toString()
                        btnNext.setOnClickListener {
                            activity!!.supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_activity_container, GameFragment(score))
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
                    gameAdapter.setData(
                        appState.questions,
                        chooseAnswer
                    )
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
        fun newInstance() = GameFragment(score = score)
    }
}