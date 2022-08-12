package com.geekbrains.gibddyola.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentMainBinding
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.ui.company.CompanyFragment
import com.geekbrains.gibddyola.ui.game.test.QuestionsFragment
import com.geekbrains.gibddyola.ui.main.recyclerView.AdaptersAvarkom
import com.geekbrains.gibddyola.ui.news.list.VkNewsFragment
import com.geekbrains.gibddyola.ui.stock.StockFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val controller by lazy { activity as ControllerOpenFragment }
    private val viewModel: MainViewModel by viewModel()

    private var adaptersAvarkom = AdaptersAvarkom {
        controller.aboutFragment(it)
        Toast.makeText(context, it.textName, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initIncomingEvents()
        banner()
    }

    private fun banner() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                Glide.with(binding.imageViewMain)
                    .load(R.mipmap.logo)
                    .centerInside()
                    .transform(RoundedCorners(15))
                    .into(binding.imageViewMain)
            }
        }
    }

    private fun initViews() {
        recyclerViewMain()
        textEditTitle()
        rotateFab()
        nextFragmentOpen()
        buttonPhone()
        viewModel.onShowListAvarkom()
    }

    private fun initIncomingEvents() {
        viewModel.repos.observe(viewLifecycleOwner) {
            adaptersAvarkom.setData(it)
        }
    }


    private fun buttonPhone() {
        binding.buttonPhone.setOnClickListener {
            val number = "709-709"
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }

    private fun recyclerViewMain() {
        binding.recyclerViewListAvarkom.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListAvarkom.adapter = adaptersAvarkom
    }

    private fun textEditTitle() {
        val textTitle = binding.textHello.text
        val spannableStringBuilder = SpannableStringBuilder(textTitle)
        val red = ForegroundColorSpan(Color.RED)
        val black = ForegroundColorSpan(Color.BLACK)
        val absoluteSizeSpanTitle = AbsoluteSizeSpan(50)

        spannableStringBuilder.setSpan(
            absoluteSizeSpanTitle,
            0,
            58,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableStringBuilder.setSpan(black, 0, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(red, 47, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textHello.text = spannableStringBuilder
    }

    private fun nextFragmentOpen() {
        binding.optionOneContainer.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                //анимация переходы
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).replace(R.id.main_activity_container, QuestionsFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
        binding.optionTwoContainer.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                //анимация переходы
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).replace(R.id.main_activity_container, StockFragment.newInstance()).addToBackStack("")
                .commit()
        }
        binding.optionThreeContainer.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                //анимация переходы
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).replace(R.id.main_activity_container, VkNewsFragment.newInstance()).addToBackStack("")
                .commit()
        }
        binding.optionFourContainer.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                //анимация переходы
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).replace(R.id.main_activity_container, CompanyFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
    }

    private fun rotateFab() {
        var openMenu = false
        val duration = 300L

        binding.btnFabMain.setOnClickListener {
            openMenu = !openMenu
            if (openMenu) {
                ObjectAnimator.ofFloat(binding.fabMainImage, View.ROTATION, 0f, 505f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -50f, -260f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -20f, -130f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionThreeContainer, View.TRANSLATION_Y, -80f, -390f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionFourContainer, View.TRANSLATION_Y, -110f, -520f)
                    .setDuration(duration).start()
                /*макет доступность*/
                binding.optionOneContainer.visibility = View.VISIBLE
                binding.optionTwoContainer.visibility = View.VISIBLE
                binding.optionThreeContainer.visibility = View.VISIBLE
                binding.optionFourContainer.visibility = View.VISIBLE
                binding.transparentBackground.isClickable = true

                binding.optionOneContainer.animate()
                    .alpha(0.8f)
                    .setDuration(duration * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(0.8f)
                    .setDuration(duration * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionThreeContainer.animate()
                    .alpha(0.8f)
                    .setDuration(duration * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionFourContainer.animate()
                    .alpha(0.8f)
                    .setDuration(duration * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0.8f).duration = duration
            } else {
                ObjectAnimator.ofFloat(binding.fabMainImage, View.ROTATION, 405f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -260f, -50f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -130f, -20f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(
                    binding.optionThreeContainer,
                    View.TRANSLATION_Y,
                    -390f,
                    -80f
                )
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionFourContainer, View.TRANSLATION_Y, -520f, -110f)
                    .setDuration(duration).start()
                binding.optionOneContainer.visibility = View.INVISIBLE
                binding.optionTwoContainer.visibility = View.INVISIBLE
                binding.optionThreeContainer.visibility = View.INVISIBLE
                binding.optionFourContainer.visibility = View.INVISIBLE
                binding.transparentBackground.isClickable = false

                binding.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(duration / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(duration / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionThreeContainer.animate()
                    .alpha(0f)
                    .setDuration(duration / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionFourContainer.animate()
                    .alpha(0f)
                    .setDuration(duration / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0f).duration = duration
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}