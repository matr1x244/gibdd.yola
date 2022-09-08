package com.geekbrains.gibddyola.game.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.geekbrains.gibddyola.ui.main.MainFragment
import kotlin.properties.Delegates

const val GAME_PREFERENCES = "gamePref"
const val GAME_SCORE = "gameScore"

class GameFragment(var questionNumber: Int) : Fragment() {
    private val viewModel: GameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }
    public var score = 0
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var chooseAnswer = -1
    var mSettings: SharedPreferences? = null
    var listOfAnsweredQuestions = mutableSetOf<Int>()
    private var numberOfQuestions: Int? = null

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
                        viewModel.setScore(score)

                        val editor: SharedPreferences.Editor = mSettings!!.edit()
                        editor.putString(GAME_SCORE, score.toString())
                        editor.apply()

                        btnNext.setOnClickListener {
                            viewModel.addAnsweredQuestion(question.id)
                            viewModel.addAnsweredQuestion2(question.id)
                            Log.d("GameLog", "question.id -${question.id} ")
                            Log.d(
                                "GameLog",
                                "AnswerdQUestionList -${viewModel.getListAnsweredQuestion()} "
                            )
                            Log.d("GameLog", "AnswerdQUestion -${listOfAnsweredQuestions} ")
                            val bundle= Bundle()
                            bundle.putInt("numberOfQuestions", numberOfQuestions!!)
                          val  nextFragment = GameFragment(changeQuestion(viewModel.getListAnsweredQuestion()))
                              nextFragment.arguments = bundle
                            if ((viewModel.getQuestionCount() - 1) > viewModel.getListAnsweredQuestion().size) {
                                activity!!.supportFragmentManager
                                    .beginTransaction()
                                    .replace(
                                        R.id.main_activity_container,
                                        nextFragment
                                    )
                                    .addToBackStack("")
                                    .commitAllowingStateLoss()

                            } else {
                                tvOutOfQuestions.visibility = View.VISIBLE
                                llGameFragment.visibility = View.GONE
                            }
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
    ): View {
//        numberOfQuestions = savedInstanceState?.getInt("numberOfQuestions", 0) ?: 0
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberOfQuestions = arguments?.getInt("numberOfQuestions")

        mSettings = context?.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
//        numberOfQuestions=  savedInstanceState?.getInt("numberOfQuestions") ?: 0
        with(binding)
        {
//           numberOfQuestions= viewModel.getNumberOfQuestion().value
            Log.d("GameLog", "Кол-во вопросов = ${numberOfQuestions} ")
//            Log.d("GameLog", "Кол-во вопросов viewModel = ${viewModel.getNumberOfQuestion().value} ")

            if (numberOfQuestions != null) radioGroup.visibility = View.GONE
//Счет игры
            viewModel.getScore().observe(viewLifecycleOwner) {
                tvScore.text = it.toString()
            }
//Количество пройденных вопросов
            viewModel.getAnsweredQuestions().observe(viewLifecycleOwner) {
                listOfAnsweredQuestions.add(it)
            }
//Кнопка Начать игру
            btnBeginGame.setOnClickListener {
                btnBeginGame.visibility = View.GONE
                llCountOfQuestions.visibility = View.GONE
                tvCountQuestions.visibility = View.VISIBLE
                numberOfQuestions = when (radioGroup.checkedRadioButtonId) {
                    R.id.rb_20 -> 20
                    R.id.rb_50 -> 50
                    R.id.rb_100 -> 100
                    R.id.rb_all -> viewModel.getQuestionCount()
                    else -> 0
                }
                Log.d("GameLog", "Кол-во вопросов numberOfQuestions= ${numberOfQuestions} ")

                tvCountQuestions.text = getString(R.string.tv_count_questions, 0, numberOfQuestions)
                viewModel.setNumberOfQuestion(numberOfQuestions!!)
                Log.d("GameLog", "Кол-во вопросов viewModel = ${viewModel.getNumberOfQuestion().value} ")

                val changeQuestion = changeQuestion(viewModel.getListAnsweredQuestion())
                val nextFragment =
                    if (changeQuestion >= 0) {
                        GameFragment(changeQuestion)
                    } else {
                        GameFragmentOutOfQuestions()
                    }
                val bundle= Bundle()
                bundle.putInt("numberOfQuestions", numberOfQuestions!!)
                nextFragment.arguments = bundle
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.main_activity_container,
                        nextFragment
                    )
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }

//Кнопка Выйти из игры
            btnQuitGame.setOnClickListener {
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_container, MainFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
            if (questionNumber != -1) {
                viewModel.getQuestion(questionNumber)
                rvAnswers.layoutManager =
                    LinearLayoutManager(requireContext())
                viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
            } else {
                llGameFragment.visibility = View.GONE

            }
        }
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    tvQuestionNumber.text = "Вопрос: " + appState.questions.id.toString()
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

    private fun randomQuestion(): Int {
        return (Math.random() * viewModel.getQuestionCount()).toInt()
    }

    fun changeQuestion(answeredQuestions: List<Int>): Int {
        if (answeredQuestions.size >= viewModel.getQuestionCount()) return -1
        while (true) {
            val randomQuestion = randomQuestion()
            if (!answeredQuestions.contains(randomQuestion)) return randomQuestion
        }
        return -1
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(question: QuestionDomain, position: Int)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        numberOfQuestions?.let { outState.putInt("numberOfQuestions", it) };

    }

//    companion object {
//        fun newInstance() = GameFragment(questionNumber = -1)
//    }
}