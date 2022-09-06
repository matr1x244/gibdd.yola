package com.geekbrains.gibddyola.ui.status

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusBinding
import com.geekbrains.gibddyola.ui.status.viewpager.GIBBD
import com.geekbrains.gibddyola.ui.status.viewpager.VIN
import com.geekbrains.gibddyola.ui.status.viewpager.ViewPagerAdapter
import com.geekbrains.gibddyola.ui.stock.viewpager.ZoomOutPageTransformer
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.showSnackBarNoAction
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class AutoStatusFragment :
    ViewBindingFragment<FragmentAutoStatusBinding>(FragmentAutoStatusBinding::inflate) {

    companion object {
        fun newInstance() = AutoStatusFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabs()
    }

    private fun tabs() {
        binding.autoStatusContainer.showSnackBarNoAction(
            R.string.internet_vk_news,
            Snackbar.LENGTH_SHORT
        )
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(
            binding.tabLayoutAutoStatus, binding.viewPager
        ) { tab, position ->
            tab.text = when (position) {
                VIN -> "Проверка дефектов"
                GIBBD -> "Проверка штрафов"
                else -> "Проверка дефектов"
            }
        }.attach()

        binding.tabLayoutAutoStatus.getTabAt(VIN)?.setIcon(R.drawable.ic_status_vin)
        binding.tabLayoutAutoStatus.getTabAt(GIBBD)?.setIcon(R.drawable.ic_checklist_auto)
    }

}
