package com.geekbrains.gibddyola.game.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.MainActivity
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameBinding
import com.geekbrains.gibddyola.game.domain.entity.AppState
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain
import com.geekbrains.gibddyola.ui.main.MainFragment

const val GAME_PREFERENCES = "gamePref"
const val GAME_SCORE = "gameScore"

class GameFragment(private var questionNumber: Int) : Fragment(), MainActivity.IOnBackPressed {
    private val viewModel: GameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var score = 0
    private var clickedAnswerPosition = -1
    var mSettings: SharedPreferences? = null
    var listOfAnsweredQuestions = mutableSetOf<Int>()     //Лист отвеченных вопросов
    private var numberOfQuestions: Int? = null            //Кол-во вопросов, выбранных пользователем
    private var countOfAnsweredQuestions = 0              //Счетчик отвеченных вопросов

    private val gameAdapter: GameFragmentAdapter by lazy {
        GameFragmentAdapter(object :
            OnItemViewClickListener {
            override fun onItemViewClick(question: QuestionDomain, position: Int) {
                with(binding) {
                    clickedAnswerPosition = position
                    btnNext.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(btnNext, View.ALPHA, 0.2f, 1.0f)
                        .setDuration(400)
                        .start()

                    tvAnswerComment.text = question.answer_about
                    tvAnswerComment.visibility = View.VISIBLE

                    ObjectAnimator.ofFloat(tvAnswerComment, View.ALPHA, 0.2f, 1.0f)
                        .setDuration(400)
                        .start()

                    viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
                    countOfAnsweredQuestions++
                    if (question.answers[position].second) score++
                    viewModel.setScore(score)

                    val editor: SharedPreferences.Editor = mSettings!!.edit()
                    editor.putString(GAME_SCORE, score.toString())
                    editor.apply()

                    btnNext.setOnClickListener {
                        viewModel.addAnsweredQuestion(question.id.toInt())
                        viewModel.addAnsweredQuestion2(question.id.toInt())

                        listOfAnsweredQuestions.add(question.id.toInt())

                        val bundle = Bundle()
                        bundle.putInt("numberOfQuestions", numberOfQuestions!!)
                        bundle.putInt("score", score)
                        bundle.putInt("questionsNumber", countOfAnsweredQuestions)
                        bundle.putIntArray(
                            "listOfAnsweredQuestions",
                            listOfAnsweredQuestions.toIntArray()
                        )
                        val nextFragment =
                            GameFragment(changeQuestion(listOfAnsweredQuestions.toList()))
                        nextFragment.arguments = bundle
                        val resultFragment = ResultsQuestionsFragment()
                        resultFragment.arguments = bundle
                        if (numberOfQuestions!! > countOfAnsweredQuestions) {
                            activity!!.supportFragmentManager
                                .beginTransaction()
                                .setCustomAnimations(
                                    //анимация переходы
                                    R.anim.to_left_in,
                                    R.anim.to_left_out,
                                    R.anim.to_right_in,
                                    R.anim.to_right_out
                                )
                                .replace(
                                    R.id.main_activity_container,
                                    nextFragment
                                )
//                                .addToBackStack("")
                                .commitAllowingStateLoss()

                        } else {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .setCustomAnimations(
                                    //анимация переходы
                                    R.anim.to_left_in,
                                    R.anim.to_left_out,
                                    R.anim.to_right_in,
                                    R.anim.to_right_out
                                ).replace(
                                    R.id.main_activity_container,
                                    resultFragment
                                )
                                .commit()
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
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberOfQuestions = arguments?.getInt("numberOfQuestions")
        score = arguments?.getInt("score") ?: 0
        countOfAnsweredQuestions = arguments?.getInt("questionsNumber") ?: 0
        listOfAnsweredQuestions =
            arguments?.getIntArray("listOfAnsweredQuestions")?.toMutableSet() ?: mutableSetOf<Int>()

        mSettings = context?.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        with(binding)
        {
            if (numberOfQuestions != null) {
                settingLayoutCountOfQuestions.visibility = View.GONE
                llHeaderRightAnswers.visibility = View.VISIBLE
                textViewHeader.visibility = View.GONE
                imageAutoSchoolLogo.visibility = View.GONE
            }
//Счет игры
            textViewScore.text = score.toString()
//Количество пройденных вопросов
            viewModel.getAnsweredQuestions().observe(viewLifecycleOwner) {
                listOfAnsweredQuestions.add(it)
            }
            //Кнопка Начать игру
            btnBeginGame.setOnClickListener {
                btnBeginGame.visibility = View.GONE
                settingLayoutCountOfQuestions.visibility = View.GONE
                textViewHeader.visibility = View.GONE
                imageAutoSchoolLogo.visibility = View.GONE
                numberOfQuestions = when (radioGroup.checkedRadioButtonId) {
                    R.id.rb_20 -> 20
                    R.id.rb_50 -> 50
                    R.id.rb_100 -> 100
                    R.id.rb_all -> viewModel.getQuestionCount()
                    else -> 0
                }
                llHeaderRightAnswers.visibility = View.VISIBLE
                viewModel.setNumberOfQuestion(numberOfQuestions!!)

                val changeQuestion = changeQuestion(viewModel.getListAnsweredQuestion())
                val nextFragment =
                    if (changeQuestion >= 0) {
                        GameFragment(changeQuestion)
                    } else {
                        ResultsQuestionsFragment()
                    }
                val bundle = Bundle()
                bundle.putInt("numberOfQuestions", numberOfQuestions!!)
                nextFragment.arguments = bundle
                requireActivity().supportFragmentManager
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
                requireActivity().supportFragmentManager
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
                gameLayoutWindow.visibility = View.GONE
            }
        }
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                with(binding) {
                    tvQuestion.text = appState.questions.question
                    appState.questions.image?.let { ivImageQuestion.setImageResource(it) }
                    gameAdapter.setData(
                        appState.questions,
                        clickedAnswerPosition
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

    //Реализация нажатия кнопки назад во фрагментах
    override fun onBackPressed(): Boolean {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_activity_container,
                GameFragment(-1)
            )
//            .addToBackStack("")
            .commitNow()
        return true
    }
}