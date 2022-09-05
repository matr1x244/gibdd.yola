package com.geekbrains.gibddyola.ui.about

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.databinding.FragmentAboutAvarkomDetailBinding
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom
import com.geekbrains.gibddyola.utils.CallIntent
import com.geekbrains.gibddyola.utils.ViewBindingFragment

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

        Glide.with(binding.avatar)
            .load(about?.avatar)
            .centerInside()
            .transform(RoundedCorners(10))
            .into(binding.avatar)

        binding.itemTextName.text = about?.textName
        binding.itemTextJobYear.text = about?.textJobYear
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