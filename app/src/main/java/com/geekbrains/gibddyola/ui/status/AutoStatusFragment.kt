package com.geekbrains.gibddyola.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAutoStatusBinding
import com.geekbrains.gibddyola.ui.status.viewpager.GIBBD
import com.geekbrains.gibddyola.ui.status.viewpager.ViewPagerAdapter
import com.geekbrains.gibddyola.ui.stock.viewpager.ZoomOutPageTransformer
import com.geekbrains.gibddyola.utils.showSnackBarNoAction
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class AutoStatusFragment : Fragment() {

    companion object {
        fun newInstance() = AutoStatusFragment()
    }

    private var _binding: FragmentAutoStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAutoStatusBinding.inflate(inflater, container, false)
        return binding.root

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
                GIBBD -> "Официальная проверка автомобиля по VIN"
//                PCA -> "РСА"
                else -> "Официальная проверка автомобиля по VIN"
            }
        }.attach()

        binding.tabLayoutAutoStatus.getTabAt(GIBBD)?.setIcon(R.drawable.ic_checklist_auto)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
