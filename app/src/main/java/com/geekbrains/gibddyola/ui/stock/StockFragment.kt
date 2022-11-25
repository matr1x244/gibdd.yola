package com.geekbrains.gibddyola.ui.stock

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentStockBinding
import com.geekbrains.gibddyola.ui.stock.viewpager.*
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
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
        tabBadgeRemove()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun viewPagerCustom() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.setPageTransformer(RotateUpPageTransformer())
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.icon = when (position) {
//                ONE_STOCK_KEY -> getString(R.string.discount_1)
//                TWO_STOCK_KEY -> getString(R.string.discount_2)
//                THREE_STOCK_KEY -> getString(R.string.discount_3)
//                FOUR_STOCK_KEY -> getString(R.string.discount_4)
                ONE_STOCK_KEY -> resources.getDrawable(
                    R.drawable.ic_car_crash,
                    resources.newTheme()
                )
                TWO_STOCK_KEY -> resources.getDrawable(
                    R.drawable.ic_heart_like,
                    resources.newTheme()
                )
                THREE_STOCK_KEY -> resources.getDrawable(
                    R.drawable.ic_company,
                    resources.newTheme()
                )
                FOUR_STOCK_KEY -> resources.getDrawable(R.drawable.ic_game, resources.newTheme())
                else -> resources.getDrawable(R.drawable.ic_game, resources.newTheme())
            }

        }.attach()

        badgeAdd(1)
        badgeAdd(2)

//        binding.tabLayout.getTabAt(ONE_STOCK_KEY)?.setIcon(R.drawable.ic_stock)
//        binding.tabLayout.getTabAt(TWO_STOCK_KEY)?.setIcon(R.drawable.ic_stock)
//        binding.tabLayout.getTabAt(THREE_STOCK_KEY)?.setIcon(R.drawable.ic_stock)
    }

    private fun badgeRemove(position: Int) {
            binding.tabLayout.getTabAt(position)?.removeBadge()
    }

    private fun tabBadgeRemove() {
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(requireActivity(), "onTabSelected IF", Toast.LENGTH_SHORT).show()
                if(binding.tabLayout.getTabAt(2)?.isSelected == true){
                    badgeRemove(2)
                } else {
                    Toast.makeText(requireActivity(), "onTabSelected ELSE", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(requireActivity(), "onTabUnselected", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(requireActivity(), "onTabReselected", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun badgeAdd(position: Int) {
        val badgeDrawable: BadgeDrawable? = binding.tabLayout.getTabAt(position)?.orCreateBadge
        badgeDrawable?.isVisible = true
        badgeDrawable?.number = 1
        badgeDrawable?.badgeTextColor = resources.getColor(R.color.white)
        badgeDrawable?.backgroundColor = resources.getColor(R.color.black)
        badgeDrawable?.badgeGravity = BadgeDrawable.BOTTOM_END
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}

