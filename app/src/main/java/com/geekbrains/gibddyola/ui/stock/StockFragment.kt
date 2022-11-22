package com.geekbrains.gibddyola.ui.stock

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentStockBinding
import com.geekbrains.gibddyola.ui.stock.viewpager.*
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class StockFragment : ViewBindingFragment<FragmentStockBinding>(FragmentStockBinding::inflate) {

    companion object {
        fun newInstance() = StockFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onViewCreated(view, savedInstanceState)

        viewPagerCustom()
    }

    private fun viewPagerCustom() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.setPageTransformer(RotateUpPageTransformer())

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                ONE_STOCK_KEY -> getString(R.string.discount_1)
                TWO_STOCK_KEY -> getString(R.string.discount_2)
                THREE_STOCK_KEY -> getString(R.string.discount_3)
                else -> getString(R.string.discount_1)
            }
        }.attach()

//        binding.tabLayout.getTabAt(ONE_STOCK_KEY)?.setIcon(R.drawable.ic_stock)
//        binding.tabLayout.getTabAt(TWO_STOCK_KEY)?.setIcon(R.drawable.ic_stock)
//        binding.tabLayout.getTabAt(THREE_STOCK_KEY)?.setIcon(R.drawable.ic_stock)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}