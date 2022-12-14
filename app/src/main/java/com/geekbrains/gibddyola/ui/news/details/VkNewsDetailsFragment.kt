package com.geekbrains.gibddyola.ui.news.details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity
import com.geekbrains.gibddyola.databinding.FragmentVkNewsDetailsBinding
import com.geekbrains.gibddyola.ui.news.details.recyclerView.OnDetailsItemClickListener
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsImageRVAdapter
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsVideoRVAdapter
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import com.geekbrains.gibddyola.utils.ViewBindingFragment
import com.geekbrains.gibddyola.utils.vkontakte.ConvertCounts
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class VkNewsDetailsFragment :
    ViewBindingFragment<FragmentVkNewsDetailsBinding>(FragmentVkNewsDetailsBinding::inflate) {

    private var itemData: VkNewsEntity.Response.Item? = null
    private var groupData: VkGroupEntity.Response? = null

    private val scope by lazy {
        getKoin().getOrCreateScope<VkNewsDetailsFragment>(SCOPE_ID)
    }

    private lateinit var viewModel: VkNewsViewModel

    private val imageAdapter: VkNewsDetailsImageRVAdapter by lazy {
        scope.get(named("vk_news_details_image_rv_adapter"))
    }

    private val videoAdapter: VkNewsDetailsVideoRVAdapter by lazy {
        scope.get(named("vk_news_details_video_rv_adapter"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemData = this.arguments?.getParcelable(ITEM_ID)
        groupData = this.arguments?.getParcelable(GROUP_ID)
        initRV()
        setData()
        setAdapterClicker()
        imageOrTextSelector()
    }

    companion object {
        private const val SCOPE_ID = "SCOPE_DETAILS_ID"
        private const val ITEM_ID = "ITEM_ID"
        private const val GROUP_ID = "GROUP_ID"
        fun newInstance(
            item: VkNewsEntity.Response.Item,
            groupData: VkGroupEntity.Response,
            viewModel: VkNewsViewModel
        ): VkNewsDetailsFragment {
            val fragment = VkNewsDetailsFragment()
            fragment.viewModel = viewModel
            fragment.arguments = Bundle()
            fragment.arguments?.putParcelable(ITEM_ID, item)
            fragment.arguments?.putParcelable(GROUP_ID, groupData)
            return fragment
        }
    }

    private fun initRV() {
        binding.vkNewsDetailsRvImage.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        binding.vkNewsDetailsRvImage.adapter = imageAdapter

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.vkNewsDetailsRvImage)

        binding.vkNewsDetailsRvVideo.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        binding.vkNewsDetailsRvVideo.adapter = videoAdapter
    }

    private fun setData() {
        if (itemData != null) {
            imageAdapter.setData(itemData!!)
            videoAdapter.setData(itemData!!)
            setText()
        }
        if (itemData?.attachments.isNullOrEmpty()) {
            binding.vkNewsDetailsStockPhoto.visibility = View.VISIBLE
        } else {
            binding.vkNewsDetailsStockPhoto.visibility = View.GONE
        }
    }

    private fun setText() {
        if (binding.vkNewsDetailsTextView.text.isNullOrEmpty()) {
            binding.vkNewsDetailsTextView.text = itemData!!.text
            binding.vkNewsDetailsTextView.movementMethod = ScrollingMovementMethod()
        }
        if (itemData?.text.isNullOrEmpty()) {
            binding.vkNewsDetailsTextView.text = groupData!!.description
        }
        binding.likeVk.text = ConvertCounts.convert(itemData!!.likes.count)
        binding.seePostVk.text = ConvertCounts.convert(itemData!!.views.count)
    }

    private fun setAdapterClicker() {
        videoAdapter.setOnDetailsItemClickListener(object : OnDetailsItemClickListener {
            override fun onItemClick(position: Int) {
                val videoUrl = getString(
                    R.string.vk_video_url,
                    itemData?.attachments?.get(position)?.video?.owner_id,
                    itemData?.attachments?.get(position)?.video?.id
                )

                binding.vkNewsDetailsWebView.loadUrl(videoUrl)
            }
        })
    }

    private fun imageOrTextSelector() {
        if (itemData?.attachments?.get(0)?.type == "photo") {
            binding.vkNewsDetailsRvVideo.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        viewModel.blockScreen(false)
        super.onDestroy()
    }
}