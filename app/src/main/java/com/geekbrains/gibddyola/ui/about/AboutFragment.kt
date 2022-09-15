package com.geekbrains.gibddyola.ui.about

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.databinding.FragmentAboutAvarkomDetailBinding
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.utils.CallIntent
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.behavior.ButtonBehavior

class AboutFragment :
    ViewBindingFragment<FragmentAboutAvarkomDetailBinding>(FragmentAboutAvarkomDetailBinding::inflate) {

    companion object {
        const val ARGS_KEY = "ARGS_KEY"

        fun newInstance(entityData: EntityAvarkom?) = AboutFragment().apply {
            arguments = Bundle()
            arguments?.putParcelable(ARGS_KEY, entityData)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailAbout()
        buttonPhone()
    }

    private fun detailAbout() {
        val about = detailAboutArguments()
        val behavior = ButtonBehavior(requireContext())

        (binding.buttonPhone.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior

        Glide.with(binding.avatar)
            .load(about?.avatar)
            .into(binding.avatar)

        binding.itemTextName.text = about?.textName
        binding.itemTextBio.text = about?.textAbout
    }

    private fun detailAboutArguments(): EntityAvarkom? {
        return arguments?.getParcelable(ARGS_KEY)
    }

    private fun buttonPhone() {
        binding.buttonPhone.setOnClickListener {
            CallIntent.check(requireActivity())
        }
    }
}