package com.geekbrains.gibddyola.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentAboutBinding
import com.geekbrains.gibddyola.domain.EntityAvarkom

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

//        val url_path_posters_adapter = "https://st.kp.yandex.net/images/film_big/${films.id}.jpg"
//        itemPoster.load(url_path_posters_adapter) {
//            precision(Precision.EXACT)
//            error(R.drawable.ic_video)
//            scale(Scale.FILL)}

        binding.avatar.setImageResource(R.mipmap.profile)
        binding.itemTextName.text = about?.textName
        binding.itemTextAbout.text = about?.textAbout
    }

    private fun detailAboutArguments(): EntityAvarkom? {
        return arguments?.getParcelable(ARGS_KEY)
    }

    private fun buttonPhone() {
        val number = "709-709"
        binding.buttonPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}