package com.geekbrains.gibddyola.ui.status.viewpager

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geekbrains.gibddyola.ui.status.AutoStatusFragment
import com.geekbrains.gibddyola.ui.status.AutoStatusPoliceFragment
import com.geekbrains.gibddyola.ui.status.AutoStatusVinFragment

const val VIN = 0
const val GIBBD = 1

class ViewPagerAdapter(fragmentManager: AutoStatusFragment) :
    FragmentStateAdapter(fragmentManager) {

    private val fragments = arrayOf(AutoStatusVinFragment(),AutoStatusPoliceFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}