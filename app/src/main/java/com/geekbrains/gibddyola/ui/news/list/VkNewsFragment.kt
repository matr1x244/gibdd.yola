package com.geekbrains.gibddyola.ui.news.list

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity
import com.geekbrains.gibddyola.databinding.FragmentVkNewsBinding
import com.geekbrains.gibddyola.ui.news.details.VkNewsDetailsFragment
import com.geekbrains.gibddyola.ui.news.list.recyclerView.OnItemClickListener
import com.geekbrains.gibddyola.ui.news.list.recyclerView.VkNewsRVAdapter
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.showSnackBarNoAction
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class VkNewsFragment : ViewBindingFragment<FragmentVkNewsBinding>(FragmentVkNewsBinding::inflate),
    VkNewsContract.View {

    private val newsList = mutableListOf<VkNewsEntity.Response.Item>()
    private val groupInfo = mutableListOf<VkGroupEntity.Response>()

    private val scope by lazy {
        getKoin().getOrCreateScope<VkNewsFragment>(SCOPE_ID)
    }

    private val adapter: VkNewsRVAdapter by lazy {
        scope.get(named("vk_news_rv_adapter"))
    }

    private val viewModel: VkNewsViewModel by lazy {
        scope.get(named("vk_news_view_model"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        onError()
        setAdapterClicker()
        checkConnection()
        isBlocked()
    }

    companion object {
        fun newInstance() = VkNewsFragment()
        const val SCOPE_ID = "vkNewsScope"
    }

    private fun initRV() {
        binding.vkNewsRvList.layoutManager = LinearLayoutManager(requireContext())
        binding.vkNewsRvList.adapter = adapter
    }

    override fun setData() {
        viewModel.vkNews.observe(viewLifecycleOwner) { news ->
            if (news.isNotEmpty()) {
                newsList.addAll(news)
                viewModel.groupInfo.observe(viewLifecycleOwner) { info ->
                    groupInfo.addAll(info)
                    adapter.setData(newsList, groupInfo)
                }
            }
        }
    }

    override fun onProcessLoading() {
        viewModel.inProgress.observe(viewLifecycleOwner) {
            if (it) {
                binding.vkNewsLoadingProcessLayout.visibility = View.VISIBLE
            } else {
                binding.vkNewsLoadingProcessLayout.visibility = View.GONE
            }
        }
    }

    override fun onError() {
        viewModel.onError.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.vkNewsFragmentContainer.showSnackBarNoAction(
                    R.string.no_internet_vk_news,
                    Snackbar.LENGTH_LONG
                )
            }
        }
    }

    override fun checkConnection() {
        onProcessLoading()
        viewModel.connectionCheck()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionStatus.collect { status ->
                    when (status) {
                        true -> {
                            disableBackground(false)
                            viewModel.setNews()
                            ObjectAnimator.ofFloat(binding.vkNewsRvList, View.TRANSLATION_Y, 2000.0f, 0.0f)
                                .setDuration(700)
                                .start()
                            setData()
                            ObjectAnimator.ofFloat(binding.vkNewsFragmentContainer, View.ALPHA, 0.4f, 1.0f)
                                .setDuration(1200)
                                .start()
                        }
                        else -> {
                            disableBackground(true)
                        }
                    }
                }
            }
        }
    }

    private fun setAdapterClicker() {
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.zoom_in,
                    R.anim.zoom_out
                )
                    .add(
                        R.id.main_activity_container, VkNewsDetailsFragment.newInstance(
                            newsList[position],
                            groupInfo[0],
                            viewModel
                        )
                    )
                    .addToBackStack(null)
                    .commit()
                viewModel.blockScreen(true)
            }
        })
    }

    private fun isBlocked() {
        viewModel.isBlocked.observe(viewLifecycleOwner) {
            disableBackground(it)
        }
    }

    private fun disableBackground(isBlock: Boolean) {
        if (isBlock) {
            binding.vkNewsHidingScreen.visibility = View.VISIBLE
        } else {
            binding.vkNewsHidingScreen.visibility = View.GONE
        }
        binding.vkNewsHidingScreen.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}