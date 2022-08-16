package com.geekbrains.gibddyola.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.gibddyola.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val gameAdapter: GameFragmentAdapter by lazy {
        GameFragmentAdapter(object :
            OnItemViewClickListener {
            override fun onItemViewClick(data: String) {
                Toast.makeText(requireContext(), "Открываю: $data", Toast.LENGTH_SHORT).show()
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
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(data: String)
    }
    companion object {
        fun newInstance() = GameFragment()
    }
}