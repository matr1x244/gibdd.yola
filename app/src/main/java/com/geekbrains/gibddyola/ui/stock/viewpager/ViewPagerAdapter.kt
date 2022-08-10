package com.geekbrains.gibddyola.ui.stock.viewpager

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geekbrains.gibddyola.ui.stock.OneStockFragment
import com.geekbrains.gibddyola.ui.stock.StockFragment
import com.geekbrains.gibddyola.ui.stock.ThreeStockFragment
import com.geekbrains.gibddyola.ui.stock.TwoStockFragment

const val ONE_STOCK_KEY = 0
const val TWO_STOCK_KEY = 1
const val THREE_STOCK_KEY = 2

class ViewPagerAdapter(fragmentManager: StockFragment) :
    FragmentStateAdapter(fragmentManager) {

    private val fragments = arrayOf(OneStockFragment(), TwoStockFragment(), ThreeStockFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}