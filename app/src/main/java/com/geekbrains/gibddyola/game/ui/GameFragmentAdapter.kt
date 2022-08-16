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
    private var trueAnswer = false
    private var isChecked = false
    private var checkedAnswer = -1

    fun setData(answers: QuestionDomain, trueAnswer: Boolean, checkedAnswer: Int) {
        this.answers = answers
        this.trueAnswer = trueAnswer
        this.checkedAnswer = checkedAnswer
        notifyDataSetChanged()
    }

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(question: QuestionDomain, position: Int, trueAnswer: Boolean) = with(binding) {
            tvAnswerItem.text = question.answers[position].first
            if (checkedAnswer == position) {
                root.setBackgroundColor(Color.YELLOW)
            }
            if (trueAnswer && question.answers[position].second) {
                root.setBackgroundColor(Color.GREEN)
            } else if (trueAnswer && !question.answers[position].second && checkedAnswer == position) {
                root.setBackgroundColor(Color.RED)
            }
            root.setOnClickListener {
                if (!isChecked) {
                    itemClickListener.onItemViewClick(answers, position)
                    root.setBackgroundColor(Color.YELLOW)
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
        holder.bind(answers, position, trueAnswer)
    }

    override fun getItemCount() = answers.answers.size

}
