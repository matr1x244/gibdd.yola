package com.geekbrains.gibddyola.ui.game.test

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.LocalRepositoryGameImpl
import com.geekbrains.gibddyola.databinding.FragmentQuestionsBinding

import com.geekbrains.gibddyola.domain.game.Question
import java.util.*
import kotlin.collections.ArrayList

var score = 0

class QuestionsFragment : Fragment() {

    private var currentQuestionId = -1
    private var selectedAnswers = mutableMapOf<Int, String>()

    private val originalOptionTextColor = Color.parseColor("#4A4A4A")

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = QuestionsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun stopGame() {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container_questions_fragment, ResultsQuestionsFragment.newInstance())
                ?.commitNow()
        }


        val allOptions = arrayListOf(
            binding.textviewAnswerOne,
            binding.textviewAnswerTwo,
            binding.textviewAnswerThree,
            binding.textviewAnswerFour
        )
        val questions: ArrayList<Question> = LocalRepositoryGameImpl().getQuestions()

        //        следующий вопрос
        fun changeQuestion() {
            // Go to results screen if it's the end of questions Array
            if (currentQuestionId + 1 == questions.size) {
                return stopGame()
            }
            currentQuestionId += 1

            val question = questions[currentQuestionId]

            binding.textviewTittle.text = question.text

            Glide.with(binding.imageQuestion)
                .load(question.image)
                .centerInside()
                .transform(RoundedCorners(15))
                .error(R.mipmap.none_image_question)
                .into(binding.imageQuestion)

            binding.progressBar.progress = currentQuestionId

            binding.textviewAnswerOne.text = question.answer1
            binding.textviewAnswerTwo.text = question.answer2
            binding.textviewAnswerThree.text = question.answer3
            if (question.answer4 != null) {
                binding.textviewAnswerFour.text = question.answer4
            } else {
                binding.textviewAnswerFour.visibility = View.INVISIBLE
            }

        }

        fun resetOptionsColor() {
            for (option in allOptions) {
                option.setTextColor(originalOptionTextColor)
                option.typeface = Typeface.DEFAULT
                option.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.color.yellow
                )
            }
        }

        // Add color changing listener in all options
        for (option in allOptions) {
            option.setOnClickListener {
                resetOptionsColor() // To prevent multi-selection

                option.setTextColor(Color.parseColor("#417EFF"))
                option.background = ContextCompat.getDrawable(
                    requireActivity(),
                    R.color.purple_500
                )
                selectedAnswers[currentQuestionId] = option.text.toString()
            }
        }
        // Initial question when user presses "Start"
        changeQuestion()

        /**
         * тут логика правильных ответов
         * containsKey - значение boolean
         */
        binding.btnAnswerNext.setOnClickListener {
            if (selectedAnswers.containsKey(currentQuestionId)) {
                // If this is the last question, calculate score
                if (currentQuestionId + 1 == questions.size) {
                    for ((questionIndex, answer) in selectedAnswers) {
                        println("${questionIndex.toString()} ${answer.toString()}")
                        if (questions[questionIndex].correctAnswer == answer) {
                            score += 1
                        }
                    }
                }
                // Change question and options
                changeQuestion()
                resetOptionsColor()
            }
        }
    }

}