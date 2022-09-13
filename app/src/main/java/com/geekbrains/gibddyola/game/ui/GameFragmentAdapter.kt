package com.geekbrains.gibddyola.game.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.databinding.FragmentGameQuestionItemBinding
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

class GameFragmentAdapter(private val itemClickListener: GameFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<GameFragmentAdapter.GameViewHolder>() {

    private lateinit var binding: FragmentGameQuestionItemBinding
    private lateinit var answers: QuestionDomain
    private var isChecked = false
    private var chooseAnswer = -1

    fun setData(answers: QuestionDomain, chooseAnswer: Int) {
        this.answers = answers
        this.chooseAnswer = chooseAnswer
        notifyDataSetChanged()
    }

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(question: QuestionDomain, position: Int, isChooseAnswer: Boolean) = with(binding) {
            tvAnswerItem.text = question.answers[position].first
            if (isChooseAnswer && question.answers[position].second) {
                listGameQuestionItem.setBackgroundColor(Color.parseColor("#4CAF50"))
                tvAnswerItem.setTextColor(Color.WHITE)
            } else if (isChooseAnswer && !question.answers[position].second && chooseAnswer == position) {
                listGameQuestionItem.setBackgroundColor(Color.parseColor("#E53935"))
                tvAnswerItem.setTextColor(Color.WHITE)
            }
            root.setOnClickListener {
                if (!isChecked) {
                    itemClickListener.onItemViewClick(answers, position)
                }
                isChecked = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        binding = FragmentGameQuestionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(answers, position, isChooseAnswer(chooseAnswer))
    }

    override fun getItemCount() = answers.answers.size

    private fun isChooseAnswer(choosePosition: Int): Boolean {
        return choosePosition >= 0
    }
}
