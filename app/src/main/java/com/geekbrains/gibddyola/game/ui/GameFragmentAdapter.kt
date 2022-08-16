package com.geekbrains.gibddyola.game.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.databinding.FragmentGameQuestionItemBinding


class GameFragmentAdapter(private val itemClickListener: GameFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<GameFragmentAdapter.GameViewHolder>() {

    private lateinit var binding: FragmentGameQuestionItemBinding
    private var questions: List<String> = listOf()

    fun setData(data: List<String>) {
        questions = data
        notifyDataSetChanged()
    }

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(question: String) = with(binding) {
            tvQuestionItem.text = question
            root.setOnClickListener { itemClickListener.onItemViewClick(question) }
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
        holder.bind(questions[position])
    }

    override fun getItemCount() = questions.size

}
