package com.geekbrains.gibddyola.ui.news.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.databinding.FragmentVkNewsBinding
import com.geekbrains.gibddyola.ui.news.details.VkNewsDetailsFragment
import com.geekbrains.gibddyola.ui.news.list.recyclerView.OnItemClickListener
import com.geekbrains.gibddyola.ui.news.list.recyclerView.VkNewsRVAdapter
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import com.geekbrains.gibddyola.utils.showSnackBarNoAction
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class VkNewsFragment : Fragment(), VkNewsContract.View {

    private var _binding: FragmentVkNewsBinding? = null
    private val binding get() = _binding!!

    private var currentPosition: Int = 0

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVkNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        onProcessLoading()
        onError()
        viewModel.setNews()
        setData()
        isBlocked()
        setAdapterClicker()
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
                    "Нужна связь с космосом для обновления новостей",
                    Snackbar.LENGTH_LONG
                )
            }
        }
    }

    private fun setAdapterClicker() {
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                currentPosition = position
                binding.vkNewsHidingScreen.visibility = View.VISIBLE
                binding.vkNewsHidingScreen.isEnabled = false
                binding.vkNewsRvList.isEnabled = false
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    //анимация переходы
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                    .add(
                        R.id.main_activity_container, VkNewsDetailsFragment.newInstance(
                            newsList[position],
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
        if (!isBlock) binding.vkNewsHidingScreen.visibility = View.GONE
        adapter.isClickable = !isBlock
        binding.vkNewsRvList.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return !isBlock
            }
        }
        binding.vkNewsRvList.adapter = adapter
        if (!isBlock) binding.vkNewsRvList.scrollToPosition(currentPosition)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}