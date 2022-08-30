package com.geekbrains.gibddyola.ui.main

//Testing Branch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.geekbrains.gibddyola.BuildConfig
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.local.TooltipList
import com.geekbrains.gibddyola.databinding.FragmentMainBinding
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.ui.company.CompanyFragment
import com.geekbrains.gibddyola.ui.game.test.QuestionsFragment
import com.geekbrains.gibddyola.ui.main.recyclerView.AdaptersAvarkom
import com.geekbrains.gibddyola.ui.news.list.VkNewsFragment
import com.geekbrains.gibddyola.ui.status.AutoStatusFragment
import com.geekbrains.gibddyola.ui.stock.StockFragment
import com.geekbrains.gibddyola.utils.CallIntent
import com.geekbrains.gibddyola.utils.animation.ImageRotation
import com.geekbrains.gibddyola.utils.audio_manager.AudioManager
import com.geekbrains.gibddyola.utils.updates.ApkDelete
import com.geekbrains.gibddyola.utils.updates.IsApkExist
import com.geekbrains.gibddyola.utils.updates.UpdateData
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedTooltips: SharedPreferences
    private lateinit var sharedUpdateParameters: SharedPreferences

    private val controller by lazy { activity as ControllerOpenFragment }
    private val viewModel: MainViewModel by viewModel()

    private var localVersion: Long? = null
    private var remoteVersion: Long? = null

    private var adaptersAvarkom = AdaptersAvarkom {
        controller.aboutFragment(it)
        Toast.makeText(context, it.textName, Toast.LENGTH_SHORT).show()
    }

    private var openMenu = false
    private val durationAnimOpenMenu = 300L
    private val playSoundMain by lazy { AudioManager(requireContext()) }

    companion object {
        private const val SHARED_TOOLTIP_NAME = "shared_tooltip"
        private const val SHARED_UPDATE_NAME = "shared_update_parameters"
        private const val TOOLTIP_NUMBER = "tooltip_number"
        private const val UPDATE_DOWNLOAD_STARTED = "download_started"
        private const val UPDATE_DOWNLOAD_FINISHED = "download_finished"
        private const val UPDATE_INSTALL_STARTED = "install_started"
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

        getSharedTooltipIndex()
        setDefaultUpdateParams()
        initViews()
        initIncomingEvents()
//        banner()
    }

    private fun banner() {
        Glide.with(binding.imageViewMain)
            .load(R.mipmap.logo)
            .centerInside()
            .transform(RoundedCorners(15))
            .into(binding.imageViewMain)
    }

    private fun getSharedTooltipIndex() {
        sharedTooltips = requireActivity()
            .getSharedPreferences(SHARED_TOOLTIP_NAME, Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        viewModel.stopGettingTooltip()
        binding.textTooltip.text = ""
        getSharedTooltipIndex()
        setTooltip()
        upDateIcon()
        backStackCustom()
        updateReminder()
    }

    private fun updateReminder() {
        val fileExist = IsApkExist()
        if (getUpdateParameters() == 2 && fileExist.start()) {
            showUpdateDialog()
        }
        if (getUpdateParameters() == 0 && fileExist.start()) {
            deleteFile()
        }
    }

    private fun initViews() {
        recyclerViewMain()
        textEditTitle()
        rotateFab()
        nextFragmentOpen()
        viewModel.onShowListAvarkom()
    }

    private fun initIncomingEvents() {
        viewModel.repos.observe(viewLifecycleOwner) {
            adaptersAvarkom.setData(it)
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
        binding.tvPlayGameMenu.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            ).replace(R.id.main_activity_container, QuestionsFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
        binding.tvStockMenu.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            ).replace(R.id.main_activity_container, StockFragment.newInstance()).addToBackStack("")
                .commit()
        }
        binding.tvNewsMenu.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            ).replace(R.id.main_activity_container, VkNewsFragment.newInstance()).addToBackStack("")
                .commit()
        }
        binding.tvAboutCompanyMenu.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            ).replace(R.id.main_activity_container, CompanyFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
        binding.tvAutoStatus.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            ).replace(R.id.main_activity_container, AutoStatusFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
        binding.mainCallLayout.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            CallIntent.start(requireActivity())
        }
        binding.mainStockLayout.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.to_left_in, R.anim.to_left_out, R.anim.to_right_in, R.anim.to_right_out
            ).replace(R.id.main_activity_container, StockFragment.newInstance()).addToBackStack("")
                .commit()
        }
    }

    private fun setDefaultUpdateParams() {
        sharedUpdateParameters = requireActivity()
            .getSharedPreferences(SHARED_UPDATE_NAME, Context.MODE_PRIVATE)

        if (!sharedUpdateParameters.contains(UPDATE_DOWNLOAD_STARTED)) {
            sharedUpdateParameters.edit()
                .putBoolean(UPDATE_DOWNLOAD_STARTED, false)
                .apply()
        }
        if (!sharedUpdateParameters.contains(UPDATE_DOWNLOAD_FINISHED)) {
            sharedUpdateParameters.edit()
                .putBoolean(UPDATE_DOWNLOAD_FINISHED, false)
                .apply()
        }
        if (!sharedUpdateParameters.contains(UPDATE_INSTALL_STARTED)) {
            sharedUpdateParameters.edit()
                .putBoolean(UPDATE_INSTALL_STARTED, false)
                .apply()
        }
    }

    private fun setTooltip() {
        val tooltipSize = TooltipList.getTooltipSize()
        var toolTipChars = ""
        var currentTooltipNumber = 0
        if (sharedTooltips.contains(TOOLTIP_NUMBER)) {
            currentTooltipNumber = sharedTooltips.getInt(TOOLTIP_NUMBER, 0)
        } else {
            val editor: SharedPreferences.Editor = sharedTooltips.edit()
            editor.putInt(TOOLTIP_NUMBER, 0)
            editor.apply()
        }
        if (currentTooltipNumber < tooltipSize - 1) {
            val editor: SharedPreferences.Editor = sharedTooltips.edit()
            editor.putInt(TOOLTIP_NUMBER, currentTooltipNumber + 1)
            editor.apply()
        } else {
            val editor: SharedPreferences.Editor = sharedTooltips.edit()
            editor.putInt(TOOLTIP_NUMBER, 0)
            editor.apply()
        }
        viewModel.setTooltipIndex(currentTooltipNumber)
        viewModel.getTooltip()

        viewModel.flowData.observe(viewLifecycleOwner) { tooltipChar ->
            toolTipChars += tooltipChar

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val spannableStringBuilder = SpannableStringBuilder(toolTipChars)
                spannableStringBuilder.setSpan(
                    BulletSpan(
                        10,
                        ContextCompat.getColor(requireContext(), R.color.light_green_600),
                        10
                    ), 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.textTooltip.text = spannableStringBuilder
            } else {
                binding.textTooltip.text = toolTipChars
            }
        }
    }

    private fun rotateFab() {
        /**
         * media player test
         */
        binding.mainMenuLayout.setOnClickListener {
            deleteFile()
            openMenu = !openMenu
            if (openMenu) {
                if (getUpdateParameters() == 0 && localVersion != remoteVersion) {
                    playSoundMain.startSoundUpDate()
                }
                ObjectAnimator.ofFloat(binding.fabMainImage, View.ROTATION, 0f, 450f)
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -50f, -260f)
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -20f, -130f)
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionThreeContainer,
                    View.TRANSLATION_Y,
                    -80f,
                    -390f
                )
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionFourContainer,
                    View.TRANSLATION_Y,
                    -110f,
                    -520f
                )
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionFiveContainer,
                    View.TRANSLATION_Y,
                    -140f,
                    -650f
                )
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionUpdateContainer,
                    View.TRANSLATION_Y,
                    -190f,
                    -850f
                )
                    .setDuration(durationAnimOpenMenu).start()
                /*макет доступность*/
                binding.optionOneContainer.visibility = View.VISIBLE
                binding.optionTwoContainer.visibility = View.VISIBLE
                binding.optionThreeContainer.visibility = View.VISIBLE
                binding.optionFourContainer.visibility = View.VISIBLE
                binding.optionFiveContainer.visibility = View.VISIBLE
                binding.transparentBackground.setOnClickListener {
                    binding.mainMenuLayout.performClick()
                    openMenu = false
                }
                binding.optionOneContainer.animate()
                    .alpha(0.8f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(0.8f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionThreeContainer.animate()
                    .alpha(0.8f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionFourContainer.animate()
                    .alpha(0.8f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionFiveContainer.animate()
                    .alpha(0.8f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionUpdateContainer.animate()
                    .alpha(0.8f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0.8f).duration = durationAnimOpenMenu
            } else {
                playSoundMain.pauseSoundAll()
                ObjectAnimator.ofFloat(binding.fabMainImage, View.ROTATION, 405f, 0f)
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -260f, -50f)
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -130f, -20f)
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionThreeContainer,
                    View.TRANSLATION_Y,
                    -390f,
                    -80f
                )
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionFourContainer,
                    View.TRANSLATION_Y,
                    -520f,
                    -110f
                )
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionFiveContainer,
                    View.TRANSLATION_Y,
                    -650f,
                    -140f
                )
                    .setDuration(durationAnimOpenMenu).start()
                ObjectAnimator.ofFloat(
                    binding.optionUpdateContainer,
                    View.TRANSLATION_Y,
                    -850f,
                    -190f
                )
                    .setDuration(durationAnimOpenMenu).start()
                binding.optionOneContainer.visibility = View.INVISIBLE
                binding.optionTwoContainer.visibility = View.INVISIBLE
                binding.optionThreeContainer.visibility = View.INVISIBLE
                binding.optionFourContainer.visibility = View.INVISIBLE
                binding.optionFiveContainer.visibility = View.INVISIBLE
                binding.optionUpdateContainer.visibility = View.INVISIBLE
                binding.downloadProcessLayout.visibility = View.INVISIBLE
                binding.transparentBackground.isClickable = false

                binding.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(durationAnimOpenMenu / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(durationAnimOpenMenu / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionThreeContainer.animate()
                    .alpha(0f)
                    .setDuration(durationAnimOpenMenu / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionFourContainer.animate()
                    .alpha(0f)
                    .setDuration(durationAnimOpenMenu / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionFiveContainer.animate()
                    .alpha(0f)
                    .setDuration(durationAnimOpenMenu / 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.optionUpdateContainer.animate()
                    .alpha(0f)
                    .setDuration(durationAnimOpenMenu * 2)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0f).duration = durationAnimOpenMenu
            }
        }
    }

    private fun upDateIcon() {
        val imageRotation = ImageRotation(binding.ivDownloadProgress)
        viewModel.checkUpdateDate()
        viewModel.isUpdateDate.observe(viewLifecycleOwner) { isUpdateDate ->
            if (isUpdateDate) {
                viewModel.getServerVersion()
                viewModel.appVersion.observe(viewLifecycleOwner) {
                    localVersion = receiveLocalVersion().toLongOrNull()
                    remoteVersion = it.toLongOrNull()

                    if (localVersion != null &&
                        remoteVersion != null &&
                        remoteVersion == localVersion
                    ) {
                        deleteFile()
                    }

                    if (localVersion != null &&
                        remoteVersion != null &&
                        remoteVersion!! > localVersion!!
                    ) {
                        if (getUpdateParameters() == 0) {
                            binding.optionUpdateContainer.visibility = View.VISIBLE
                            binding.downloadProcessLayout.visibility = View.GONE

                            val anim: Animation = AlphaAnimation(0.0f, 1.0f)
                            anim.duration = 500
                            anim.startOffset = 20
                            anim.repeatMode = Animation.REVERSE
                            anim.repeatCount = Animation.INFINITE
                            binding.optionUpdateContainer.startAnimation(anim)

                            binding.optionUpdateContainer.setOnClickListener {
                                deleteFile()
                                setUpdateParameters(UPDATE_DOWNLOAD_STARTED, true)
                                binding.optionUpdateContainer.clearAnimation()
                                binding.optionUpdateContainer.visibility = View.GONE
                                binding.downloadProcessLayout.visibility = View.VISIBLE
                                imageRotation.startAnimation()
                                playSoundMain.pauseSoundAll()
                                viewModel.downloadNewAppApk()
                                Toast.makeText(
                                    requireActivity(),
                                    UpdateData.fileName(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.downloadingProcess.observe(viewLifecycleOwner) { percent ->
                                    val percentString = getString(R.string.downloading_percent_count, percent)
                                    binding.tvDownloadCount.text = percentString
                                }
                                viewModel.downloadApkMessage.observe(viewLifecycleOwner) { message ->
                                    when (message) {
                                        UpdateData.downloadSuccess() -> {
                                            Toast.makeText(
                                                requireContext(),
                                                message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            setUpdateParameters(UPDATE_DOWNLOAD_FINISHED, true)
                                            imageRotation.stopAnimation()
                                            showUpdateDialog()
                                        }
                                    }
                                }
                            }
                        } else {
                            binding.optionUpdateContainer.visibility = View.GONE
                            playSoundMain.pauseSoundAll()
                        }

                    }
                }
            }
        }
    }

    private fun getUpdateParameters(): Int {
        var downloadStarted = false
        var downloadFinished = false

        if (sharedUpdateParameters.contains(UPDATE_DOWNLOAD_STARTED)) {
            downloadStarted = sharedUpdateParameters
                .getBoolean(UPDATE_DOWNLOAD_STARTED, false)
        }
        if (sharedUpdateParameters.contains(UPDATE_DOWNLOAD_FINISHED)) {
            downloadFinished = sharedUpdateParameters
                .getBoolean(UPDATE_DOWNLOAD_FINISHED, false)
        }

        if (downloadStarted && !downloadFinished) return 1
        if (!downloadStarted) return 0
        if (downloadStarted && downloadFinished) return 2
        return 3
    }

    private fun setUpdateParameters(parameter: String, value: Boolean) {
        val updateParamsEditor: SharedPreferences.Editor = sharedUpdateParameters.edit()
        when (parameter) {
            UPDATE_DOWNLOAD_STARTED -> {
                updateParamsEditor.putBoolean(UPDATE_DOWNLOAD_STARTED, value)
                updateParamsEditor.apply()
            }
            UPDATE_DOWNLOAD_FINISHED -> {
                updateParamsEditor.putBoolean(UPDATE_DOWNLOAD_FINISHED, value)
                updateParamsEditor.apply()
            }
            UPDATE_INSTALL_STARTED -> {
                updateParamsEditor.putBoolean(UPDATE_INSTALL_STARTED, value)
                updateParamsEditor.apply()
            }
        }
    }

    private fun showUpdateDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Обновление готово к установке")
            .setMessage("Обновить приложение?")
            .setCancelable(true)
            .setPositiveButton("Установить") { _, _ ->
                startActivity(installApk())
                setUpdateParameters(UPDATE_INSTALL_STARTED, true)
            }
            .setNegativeButton("Отложить") { _, _ ->
                setUpdateParameters(UPDATE_INSTALL_STARTED, false)
            }
        builder.create()
        builder.show()
    }

    private fun installApk(): Intent {
        val intent = Intent("android.intent.action.VIEW")
        val apkFile = File("${UpdateData.downloadPath()}/${UpdateData.fileName()}")
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            apkFile
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addCategory("android.intent.category.DEFAULT")
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        setUpdateParameters(UPDATE_DOWNLOAD_STARTED, false)
        setUpdateParameters(UPDATE_DOWNLOAD_FINISHED, false)
        setUpdateParameters(UPDATE_INSTALL_STARTED, false)
        return intent
    }

    private fun backStackCustom() {
        /**
         * custom menu back and exit app
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (openMenu) {
                        binding.mainMenuLayout.performClick()
                    } else if (
                        !requireActivity().supportFragmentManager.fragments.contains(this@MainFragment)
                    ) {
                        requireActivity().supportFragmentManager.popBackStack()
                    } else {
                        playSoundMain.exitStartSoundApp()
                        requireActivity().finish()
                    }
                }
            })
    }

    private fun deleteFile() {
        val deleter = ApkDelete()
        deleter.run()
    }

    private fun receiveLocalVersion(): String {
        return BuildConfig.VERSION_CODE.toString()
    }

    override fun onStop() {
        super.onStop()
        playSoundMain.pauseSoundAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playSoundMain.stopSoundAll()
        _binding = null
    }

}