package com.geekbrains.gibddyola.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAboutBinding
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom

class AboutFragment : Fragment() {

    companion object {
        const val ARGS_KEY = "ARGS_KEY"

        fun newInstance(entityData: EntityAvarkom?) = AboutFragment().apply {
            arguments = Bundle()
            arguments?.putParcelable(ARGS_KEY, entityData)
        }
    }

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root

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
            .into(binding.avatar)

        binding.itemTextName.text = about?.textName
        binding.itemTextJobYear.text = about?.textJobYear
        binding.itemTextBio.text = about?.textAbout
    }

    private fun detailAboutArguments(): EntityAvarkom? {
        return arguments?.getParcelable(ARGS_KEY)
    }

    private fun buttonPhone() {
        val number = "709-709"
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")

        binding.buttonPhone.setOnClickListener {
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}