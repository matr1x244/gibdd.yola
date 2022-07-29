package com.geekbrains.gibddyola.ui.main.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.BottomNavigationLayoutBinding
import com.geekbrains.gibddyola.ui.stock.StockFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    companion object {
        fun newInstance() = BottomNavigationDrawerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
//                R.id.navigation_three -> {
//                    startActivity(Intent(requireContext(), LayoutActivity::class.java))
//                }
                R.id.navigation_stock_fragment -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.main_activity_container, StockFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}