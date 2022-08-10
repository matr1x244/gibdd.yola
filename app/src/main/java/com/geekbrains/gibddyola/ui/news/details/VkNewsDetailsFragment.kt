package com.geekbrains.gibddyola.ui.news.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.databinding.FragmentVkNewsDetailsBinding
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsRVAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class VkNewsDetailsFragment : Fragment() {

    private var _binding: FragmentVkNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private val adapter: VkNewsDetailsRVAdapter by inject(named("vk_news_details_rv_adapter"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVkNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemData: VkNewsEntity.Response.Item? = this.arguments?.getParcelable(ITEM_ID)
        if (itemData != null) {
            adapter.setData(itemData)
        }
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
}