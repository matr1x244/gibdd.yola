package com.geekbrains.gibddyola.ui.news.details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.databinding.FragmentVkNewsDetailsBinding
import com.geekbrains.gibddyola.ui.news.details.recyclerView.OnDetailsItemClickListener
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsImageRVAdapter
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsVideoRVAdapter
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import com.geekbrains.gibddyola.utils.ConvertCounts
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class VkNewsDetailsFragment : Fragment() {

    private var _binding: FragmentVkNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private var itemData: VkNewsEntity.Response.Item? = null

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVkNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemData = this.arguments?.getParcelable(ITEM_ID)
        initRV()
        setData()
        setAdapterClicker()
        imageOrTextSelector()
    }

    companion object {
        private const val SCOPE_ID = "SCOPE_DETAILS_ID"
        private const val ITEM_ID = "ITEM_ID"
        fun newInstance(
            item: VkNewsEntity.Response.Item,
            viewModel: VkNewsViewModel
        ): VkNewsDetailsFragment {
            val fragment = VkNewsDetailsFragment()
            fragment.viewModel = viewModel
            fragment.arguments = Bundle()
            fragment.arguments?.putParcelable(ITEM_ID, item)
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
    }

    private fun setText() {
        if (binding.vkNewsDetailsTextView.text.isNullOrEmpty()) {
            binding.vkNewsDetailsTextView.text = itemData!!.text
            binding.vkNewsDetailsTextView.movementMethod = ScrollingMovementMethod()
        }
        if (binding.vkNewsDetailsLikesText.text.isNullOrEmpty()) {
            binding.vkNewsDetailsLikesText.text = ConvertCounts.convert(itemData!!.likes.count)
        }
        if (binding.vkNewsDetailsViewsText.text.isNullOrEmpty()) {
            binding.vkNewsDetailsViewsText.text = ConvertCounts.convert(itemData!!.views.count)
        }
    }

    private fun setAdapterClicker() {
        videoAdapter.setOnDetailsItemClickListener(object : OnDetailsItemClickListener {
            override fun onItemClick(position: Int) {
                val videoUrl = "https://vk.com/video${
                    itemData?.attachments?.get(position)?.video?.owner_id
                }_${
                    itemData?.attachments?.get(position)?.video?.id
                }"

                binding.vkNewsDetailsWebView.loadUrl(videoUrl)
            }
        })
    }

    private fun imageOrTextSelector() {
        if (itemData?.attachments?.get(0)?.type == "photo") {
            binding.vkNewsDetailsRvVideo.visibility = View.GONE
        }

        if (itemData?.attachments.isNullOrEmpty()) {
            binding.vkNewsDetailsRvImage.visibility = View.GONE
            binding.vkNewsDetailsRvVideo.visibility = View.GONE
        }

        if (itemData?.text.isNullOrEmpty()) {
            binding.vkNewsDetailsTextView.visibility = View.GONE
        }

    }

    override fun onStop() {
        super.onStop()
        viewModel.blockScreen(false)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}