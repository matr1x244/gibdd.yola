package com.geekbrains.gibddyola.ui.status.viewpager

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geekbrains.gibddyola.ui.status.AutoStatusPoliceFragment
import com.geekbrains.gibddyola.ui.status.AutoStatusFragment
import com.geekbrains.gibddyola.ui.stock.TwoStockFragment

const val GIBBD = 0
const val PCA = 1

class ViewPagerAdapter(fragmentManager: AutoStatusFragment) :
    FragmentStateAdapter(fragmentManager) {

    private val fragments = arrayOf(AutoStatusPoliceFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}