package com.geekbrains.gibddyola.game.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameBinding
import com.geekbrains.gibddyola.game.domain.entity.AppState
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain
import com.geekbrains.gibddyola.game.ui.recyclerView.GameFragmentAdapter
import com.geekbrains.gibddyola.ui.main.MainFragment
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

const val GAME_PREFERENCES = "gamePref"
const val GAME_SCORE = "gameScore"

class GameFragment(private var questionNumber: Int) : ViewBindingFragment<FragmentGameBinding>(
    FragmentGameBinding::inflate
) {

    companion object {
        fun newInstance() = GameFragment(questionNumber = 0)
    }

    private val viewModel: GameViewModel by viewModel(named("game_view_model"))

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

                    viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
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
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .setCustomAnimations(
                                    R.anim.to_left_in,
                                    R.anim.to_left_out,
                                    R.anim.to_right_in,
                                    R.anim.to_right_out
                                )
                                .replace(
                                    R.id.main_activity_container,
                                    nextFragment
                                )
                                .commit()

                        } else {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .setCustomAnimations(
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberOfQuestions = arguments?.getInt("numberOfQuestions")
        score = arguments?.getInt("score") ?: 0
        countOfAnsweredQuestions = arguments?.getInt("questionsNumber") ?: 0
        listOfAnsweredQuestions =
            arguments?.getIntArray("listOfAnsweredQuestions")?.toMutableSet() ?: mutableSetOf()

        mSettings = context?.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE)

        startGame()
        autoSchoolLogo()
    }

    private fun autoSchoolLogo() {
        Glide.with(binding.imageAutoSchoolLogo)
            .load(R.mipmap.auto_school)
            .centerInside()
            .transform(RoundedCorners(10))
            .error(R.mipmap.auto_school)
            .into(binding.imageAutoSchoolLogo)
    }

    private fun startGame() {
        if (numberOfQuestions != null) {
            binding.textViewHeader.visibility = View.GONE
            binding.settingLayoutCountOfQuestions.visibility = View.GONE
            binding.imageAutoSchoolLogo.visibility = View.GONE
            binding.llHeaderRightAnswers.visibility = View.VISIBLE
            binding.gameLayoutWindow.visibility = View.VISIBLE
        }
        //Счет игры
        binding.textViewScore.text = score.toString()
        //Количество пройденных вопросов
        viewModel.getAnsweredQuestions().observe(viewLifecycleOwner) {
            listOfAnsweredQuestions.add(it)
        }
        //Кнопка Начать игру
        binding.btnBeginGame.setOnClickListener {
            binding.btnBeginGame.visibility = View.GONE
            binding.settingLayoutCountOfQuestions.visibility = View.GONE
            numberOfQuestions = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rb_20 -> 5
                R.id.rb_50 -> 50
                R.id.rb_100 -> 100
                R.id.rb_all -> viewModel.getQuestionCount()
                else -> 0
            }
            binding.llHeaderRightAnswers.visibility = View.VISIBLE
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
                .commit()
        }
        //Кнопка Выйти из игры
        binding.btnQuitGame.setOnClickListener {
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
        if (questionNumber != -1) {
            viewModel.getQuestion(questionNumber)
            binding.rvAnswers.layoutManager =
                LinearLayoutManager(requireContext())
            viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        } else {
            binding.gameLayoutWindow.visibility = View.GONE
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        numberOfQuestions?.let { outState.putInt("numberOfQuestions", it) }

    }

//Реализация нажатия кнопки назад во фрагментах
    /*fun onBackPressed() {
        if (!blockUi.getValue()) {
            val builder: AlertDialog.Builder = Builder(activity)
            builder.setMessage(R.string.question_exit_without_saving)
                .setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, which ->
                        FragmentUtils.removeFragmentAndFireActivityResult(
                            this@InventoryBaseCreateFragment,
                            Activity.RESULT_CANCELED,
                            Intent()
                        )
                    })
                .setNegativeButton(android.R.string.cancel,
                    DialogInterface.OnClickListener { dialog, which -> })
            builder.show()
        }
    }*/
}