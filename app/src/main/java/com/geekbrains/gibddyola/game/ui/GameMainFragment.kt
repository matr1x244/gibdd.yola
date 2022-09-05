package com.geekbrains.gibddyola.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentGameMainBinding
import com.geekbrains.gibddyola.game.domain.entity.QuestionDomain

class GameMainFragment : Fragment() {
    private var _binding: FragmentGameMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnBeginGame.setOnClickListener {
                llGameFragment.visibility = View.VISIBLE
                btnBeginGame.visibility = View.GONE
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.game_fragment, GameFragment(0, 0))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }

            tvCountQuestions.text = getString(R.string.tv_count_questions, 0, 10)
        }
    }


    interface OnCheckButtonClickListener {
        fun onCheckButtonClick()
    }
}