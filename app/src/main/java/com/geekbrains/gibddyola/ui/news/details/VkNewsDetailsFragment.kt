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
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsImageRVAdapter
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsVideoRVAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class VkNewsDetailsFragment : Fragment() {

    private var _binding: FragmentVkNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private var itemData: VkNewsEntity.Response.Item? = null

    private val imageAdapter: VkNewsDetailsImageRVAdapter by
    inject(named("vk_news_details_image_rv_adapter"))

    private val videoAdapter: VkNewsDetailsVideoRVAdapter by
    inject(named("vk_news_details_video_rv_adapter"))

    private var isVideo = false
    private var isImage = false

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
        imageOrVideoSelector()
        setData()
    }

    companion object {
        private const val ITEM_ID = "ITEM_ID"
        fun newInstance(item: VkNewsEntity.Response.Item): VkNewsDetailsFragment {
            val fragment = VkNewsDetailsFragment()
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
            if (isImage) {
                imageAdapter.setData(itemData!!)
                setText()
            }
            if (isVideo) {
                videoAdapter.setData(itemData!!)
                setText()
            }
        }
    }

    private fun setText() {
        if (binding.vkNewsDetailsTextView.text.isNullOrEmpty()) {
            if (isVideo && itemData?.text?.contains("https://youtu.be") == true) {
                binding.vkNewsDetailsTextView.text =
                    itemData!!.attachments?.get(0)?.video?.description
            } else {
                binding.vkNewsDetailsTextView.text = itemData!!.text
            }
            binding.vkNewsDetailsTextView.movementMethod = ScrollingMovementMethod()
        }
    }

    private fun imageOrVideoSelector() {

        itemData?.attachments?.forEach { attachment ->
            if (attachment.type == "photo") {
                isImage = true
            }
            if (attachment.type == "video") {
                isVideo = true
            }
        }

        if (itemData?.attachments.isNullOrEmpty()) {
            binding.vkNewsDetailsRvImage.visibility = View.GONE
            binding.vkNewsDetailsRvVideo.visibility = View.GONE
        }

        if (itemData?.text.isNullOrEmpty()) {
            binding.vkNewsDetailsTextView.visibility = View.GONE
        }

        if (isImage) {
            binding.vkNewsDetailsRvImage.visibility = View.VISIBLE
        } else {
            binding.vkNewsDetailsRvImage.visibility = View.GONE
        }

        if (isVideo) {
            binding.vkNewsDetailsRvVideo.visibility = View.VISIBLE
        } else {
            binding.vkNewsDetailsRvVideo.visibility = View.GONE
        }
    }

    override fun onPause() {
        videoAdapter.releasePlayer()
        super.onPause()
    }

    override fun onStop() {
        videoAdapter.releasePlayer()
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}