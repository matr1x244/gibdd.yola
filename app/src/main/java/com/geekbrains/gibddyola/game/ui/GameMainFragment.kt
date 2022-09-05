package com.geekbrains.gibddyola.game.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameMainBinding
import com.geekbrains.gibddyola.ui.main.MainFragment

const val GAME_PREFERENCES = "gamePref"
const val GAME_SCORE = "gameScore"

class GameMainFragment : Fragment() {
    private val viewModel: GameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }

    private var _binding: FragmentGameMainBinding? = null
    private val binding get() = _binding!!
    var mSettings: SharedPreferences? = null
    private var listOfAnsweredQuestions = mutableSetOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mSettings = context?.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        _binding = FragmentGameMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
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
                llGameFragment.visibility = View.VISIBLE
                btnBeginGame.visibility = View.GONE
                llCountOfQuestions.visibility = View.GONE
                tvCountQuestions.visibility = View.VISIBLE
                tvCountQuestions.text = when (radioGroup.checkedRadioButtonId) {
                    R.id.rb_20 -> getString(R.string.tv_count_questions, 0, 20)
                    R.id.rb_50 -> getString(R.string.tv_count_questions, 0, 50)
                    R.id.rb_100 -> getString(R.string.tv_count_questions, 0, 100)
                    R.id.rb_all -> getString(R.string.tv_count_questions, 0, viewModel.getQuestionCount())
                    else -> getString(R.string.tv_count_questions, listOfAnsweredQuestions.size, viewModel.getQuestionCount())
                }
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.game_fragment, GameFragment(0))
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
        }
    }
}
