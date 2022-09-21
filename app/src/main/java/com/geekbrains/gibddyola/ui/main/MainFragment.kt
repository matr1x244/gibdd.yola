package com.geekbrains.gibddyola.ui.main


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.geekbrains.gibddyola.BuildConfig
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.FragmentMainBinding
import com.geekbrains.gibddyola.domain.employee.ControllerOpenFragment
import com.geekbrains.gibddyola.game.ui.GAME_PREFERENCES
import com.geekbrains.gibddyola.game.ui.GAME_SCORE
import com.geekbrains.gibddyola.game.ui.GameFragment
import com.geekbrains.gibddyola.ui.company.CompanyFragment
import com.geekbrains.gibddyola.ui.main.recyclerView.AdaptersAvarkom
import com.geekbrains.gibddyola.ui.news.list.VkNewsFragment
import com.geekbrains.gibddyola.ui.status.AutoStatusFragment
import com.geekbrains.gibddyola.ui.stock.StockFragment
import com.geekbrains.gibddyola.utils.CallIntent
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.animation.FragmentOpenBackStack
import com.geekbrains.gibddyola.utils.animation.ImageRotation
import com.geekbrains.gibddyola.utils.animation.MainMenuOpen
import com.geekbrains.gibddyola.utils.animation.VisibilityChanger
import com.geekbrains.gibddyola.utils.audio_manager.AudioManager
import com.geekbrains.gibddyola.utils.flow.Tooltips
import com.geekbrains.gibddyola.utils.updates.IsApkExist
import com.geekbrains.gibddyola.utils.updates.UpdateData
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.io.File


class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var sharedTooltips: SharedPreferences
    private lateinit var sharedUpdateParameters: SharedPreferences
    private lateinit var getGlobalScore: SharedPreferences

    private val controller by lazy { activity as ControllerOpenFragment }

    private val scope by lazy {
        getKoin().getOrCreateScope<MainFragment>(SCOPE_ID)
    }
    private val viewModel: MainViewModel by lazy {
        scope.get(named("mainFragmentViewModel"))
    }

    private var localVersion: Long? = null
    private var remoteVersion: Long? = null

    private val visibility = VisibilityChanger()
    private val fragmentOpenBackStack = FragmentOpenBackStack()

    private lateinit var imageRotation: ImageRotation

    private val downloadPath: String by lazy {
        scope.get(named("downloadPath"))
    }

    private var adaptersAvarkom = AdaptersAvarkom {
        controller.aboutFragment(it)
    }
    private var globalScore = 0
    private var openMenu = false
    private val playSoundMain by lazy { AudioManager(requireContext()) }

    companion object {
        private const val SHARED_TOOLTIP_NAME = "shared_tooltip"
        private const val SHARED_UPDATE_NAME = "shared_update_parameters"
        private const val TOOLTIP_NUMBER = "tooltip_number"
        private const val UPDATE_DOWNLOAD_STARTED = "download_started"
        private const val UPDATE_DOWNLOAD_FINISHED = "download_finished"
        private const val UPDATE_INSTALL_POSTPONED = "download_postponed"
        private const val UPDATE_INSTALL_STARTED = "install_started"
        private const val SCOPE_ID = "main_fragment_scope"
        private const val UPDATE_ICON = "update_icon"
        private const val DOWNLOAD_ICON = "download _icon"
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSharedTooltipIndex()
        setDefaultUpdateParams()
        initViews()
        initIncomingEvents()
        upDateIcon()
        banner()
        hisStatusText()
    }

    @SuppressLint("SetTextI18n")
    private fun hisStatusText() {
        binding.hisStatusGame.text = getString(R.string.global_score_text_view) + getHisStatusText()
        binding.hisStatusGame.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.to_left_in,
                    R.anim.to_left_out,
                    R.anim.to_right_in,
                    R.anim.to_right_out
                )
                .replace(R.id.main_activity_container, GameFragment.newInstance())
                .commit()
        }
    }

    private fun getHisStatusText(): String {
        globalScore = getGlobalScore.getInt(GAME_SCORE, 0)
        return if (globalScore == 0) {
            resources.getString(R.string.null_level_game)
        } else if (globalScore < 20) {
            resources.getString(R.string.one_level_game)
        } else if (globalScore < 50) {
            resources.getString(R.string.two_level_game)
        } else if (globalScore < 100) {
            resources.getString(R.string.three_level_game)
        } else {
            resources.getString(R.string.four_level_game)
        }
    }

    private fun banner() {
        Glide.with(binding.imageViewMain)
            .asGif()
            .load(R.mipmap.logo_animate)
            .transform(FitCenter(), CenterInside())
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
        backStackCustom()
        updateReminder()
    }

    private fun updateReminder() {
        val fileExist = IsApkExist(downloadPath)
        if (getUpdateParameters(UPDATE_DOWNLOAD_STARTED) &&
            getUpdateParameters(UPDATE_DOWNLOAD_FINISHED) &&
            fileExist.start()
        ) {
            showUpdateDialog()
        }
        if ((!getUpdateParameters(UPDATE_DOWNLOAD_STARTED) && fileExist.start()) ||
            (getUpdateParameters(UPDATE_DOWNLOAD_STARTED) &&
                    !getUpdateParameters(UPDATE_DOWNLOAD_FINISHED) &&
                    fileExist.start()) ||
            (!getUpdateParameters(UPDATE_INSTALL_POSTPONED) && fileExist.start())
        ) {
            viewModel.deleteFile()
        }
    }

    private fun initViews() {
        imageRotation = ImageRotation(binding.ivDownloadProgress)

        if (getUpdateParameters(UPDATE_DOWNLOAD_STARTED) &&
            !getUpdateParameters(UPDATE_DOWNLOAD_FINISHED)
        ) {
            setUpdateParameters(UPDATE_DOWNLOAD_STARTED, false)
        }

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
        spannableStringBuilder.insert(31, "\n")

        binding.textHello.text = spannableStringBuilder
    }

    private fun nextFragmentOpen() {
        openMenu = fragmentOpenBackStack.setClick(
            binding.tvPlayGameMenu,
            requireActivity(),
            GameFragment.newInstance(),
            playSoundMain
        )
        openMenu = fragmentOpenBackStack.setClick(
            binding.tvStockMenu,
            requireActivity(),
            StockFragment.newInstance(),
            playSoundMain
        )
        openMenu = fragmentOpenBackStack.setClick(
            binding.tvNewsMenu,
            requireActivity(),
            VkNewsFragment.newInstance(),
            playSoundMain
        )
        openMenu = fragmentOpenBackStack.setClick(
            binding.tvAboutCompanyMenu,
            requireActivity(),
            CompanyFragment.newInstance(),
            playSoundMain
        )
        openMenu = fragmentOpenBackStack.setClick(
            binding.tvAutoStatus,
            requireActivity(),
            AutoStatusFragment.newInstance(),
            playSoundMain
        )
        binding.mainCallLayout.setOnClickListener {
            openMenu = false
            playSoundMain.stopSoundAll()
            CallIntent.check(requireActivity())
        }
        openMenu = fragmentOpenBackStack.setClick(
            binding.mainStockLayout,
            requireActivity(),
            StockFragment.newInstance(),
            playSoundMain
        )
    }

    private fun setDefaultUpdateParams() {
        sharedUpdateParameters = requireActivity()
            .getSharedPreferences(SHARED_UPDATE_NAME, Context.MODE_PRIVATE)
        getGlobalScore =
            requireActivity().getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE)
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
        val tooltips = Tooltips()
        tooltips.set(
            sharedTooltips,
            binding.textTooltip,
            TOOLTIP_NUMBER,
            viewModel,
            viewLifecycleOwner,
            requireContext()
        )
    }

    private fun rotateFab() {
        val mainMenuOpen = MainMenuOpen()
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE

        binding.mainMenuLayout.setOnClickListener {
            openMenu = !openMenu

            mainMenuOpen.setAnimation(binding.fabMainImage, openMenu)
            mainMenuOpen.setAnimation(binding.optionOneContainer, openMenu)
            mainMenuOpen.setAnimation(binding.optionTwoContainer, openMenu)
            mainMenuOpen.setAnimation(binding.optionThreeContainer, openMenu)
            mainMenuOpen.setAnimation(binding.optionFourContainer, openMenu)
            mainMenuOpen.setAnimation(binding.optionFiveContainer, openMenu)
            mainMenuOpen.setAnimation(binding.transparentBackground, openMenu)
            mainMenuOpen.setAnimation(binding.downloadProcessLayout, openMenu)
            mainMenuOpen.setAnimation(binding.optionUpdateContainer, openMenu)

            if (openMenu) {
                binding.optionUpdateContainer.startAnimation(anim)
                if (!getUpdateParameters(UPDATE_DOWNLOAD_STARTED) && localVersion != remoteVersion) {
                    playSoundMain.startSoundUpDate()
                }

                visibility.change(binding.optionOneContainer, true)
                visibility.change(binding.optionTwoContainer, true)
                visibility.change(binding.optionThreeContainer, true)
                visibility.change(binding.optionFourContainer, true)
                visibility.change(binding.optionFiveContainer, true)

                if (getUpdateParameters(UPDATE_DOWNLOAD_STARTED) &&
                    !getUpdateParameters(UPDATE_DOWNLOAD_FINISHED)
                ) {
                    visibility.change(binding.downloadProcessLayout, true)
                }

                binding.transparentBackground.setOnClickListener {
                    binding.mainMenuLayout.performClick()
                }
            }

            if (!openMenu) {
                anim.cancel()
                playSoundMain.pauseSoundAll()

                visibility.change(binding.optionOneContainer, false)
                visibility.change(binding.optionTwoContainer, false)
                visibility.change(binding.optionThreeContainer, false)
                visibility.change(binding.optionFourContainer, false)
                visibility.change(binding.optionFiveContainer, false)
                visibility.change(binding.optionUpdateContainer, false)
                visibility.change(binding.downloadProcessLayout, false)

                binding.transparentBackground.isClickable = false
            }
        }
    }

    private fun upDateIcon() {
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
                        viewModel.deleteFile()
                    }

                    if (localVersion != null &&
                        remoteVersion != null &&
                        remoteVersion!! > localVersion!!
                    ) {
                        if (!getUpdateParameters(UPDATE_DOWNLOAD_STARTED)) {
                            updateIconSwitcher(UPDATE_ICON)

                            binding.optionUpdateContainer.setOnClickListener {
                                setUpdateParameters(UPDATE_DOWNLOAD_STARTED, true)
                                binding.optionUpdateContainer.clearAnimation()

                                visibility.change(binding.downloadBlockingLayout, true)
                                downloadBlockingClick(true)
                                updateIconSwitcher(DOWNLOAD_ICON)

                                imageRotation.startAnimation()
                                playSoundMain.pauseSoundAll()
                                viewModel.downloadNewAppApk()
                                Toast.makeText(
                                    requireActivity(),
                                    UpdateData.fileName(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.downloadApkMessage.observe(viewLifecycleOwner) { message ->
                                    when (message) {
                                        UpdateData.downloadSuccess() -> {
                                            Toast.makeText(
                                                requireContext(),
                                                message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            setUpdateParameters(UPDATE_DOWNLOAD_FINISHED, true)
                                            visibility.change(
                                                binding.downloadBlockingLayout,
                                                false
                                            )
                                            downloadBlockingClick(false)
                                            imageRotation.stopAnimation()
                                            showUpdateDialog()
                                        }
                                    }
                                }
                                viewModel.downloadingProcess.observe(viewLifecycleOwner) { percent ->
                                    val percentString =
                                        getString(R.string.downloading_percent_count, percent)
                                    binding.tvDownloadCount.text = percentString
                                }
                            }
                        } else {
                            visibility.change(binding.optionUpdateContainer, false)
                            playSoundMain.pauseSoundAll()
                        }

                    }
                }
            }
        }
    }

    private fun getUpdateParameters(param: String): Boolean {
        var value = false
        if (sharedUpdateParameters.contains(param)) {
            value = sharedUpdateParameters.getBoolean(param, false)
        }
        return value
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
            UPDATE_INSTALL_POSTPONED -> {
                updateParamsEditor.putBoolean(UPDATE_INSTALL_POSTPONED, value)
                updateParamsEditor.apply()
            }
        }
    }

    private fun updateIconSwitcher(iconName: String) {
        when (iconName) {
            UPDATE_ICON -> {
                visibility.change(binding.optionUpdateContainer, true)
                visibility.change(binding.downloadProcessLayout, false)
            }
            DOWNLOAD_ICON -> {
                visibility.change(binding.optionUpdateContainer, false)
                visibility.change(binding.downloadProcessLayout, true)
            }
        }
    }

    private fun showUpdateDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.update_dialog_title))
            .setMessage(getString(R.string.update_dialog_message))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.update_dialog_install_button)) { _, _ ->
                startActivity(installApk())
                setUpdateParameters(UPDATE_INSTALL_STARTED, true)
            }
            .setNegativeButton(getString(R.string.update_dialog_postponed_button)) { _, _ ->
                setUpdateParameters(UPDATE_INSTALL_STARTED, false)
                setUpdateParameters(UPDATE_INSTALL_POSTPONED, true)
            }
        builder.create()
        builder.show()
    }

    private fun installApk(): Intent {
        val intent = Intent("android.intent.action.VIEW")
        val apkFile = File("$downloadPath/${UpdateData.fileName()}")
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

    private fun downloadBlockingClick(clickable: Boolean) {
        if (clickable) {
            binding.downloadBlockingLayout.isClickable = true
            binding.downloadBlockingLayout.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_wait_downloading_end),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            binding.downloadBlockingLayout.isClickable = false
        }
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
    }

}