package com.geekbrains.gibddyola.ui.news.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.databinding.FragmentVkNewsBinding
import com.geekbrains.gibddyola.ui.news.details.VkNewsDetailsFragment
import com.geekbrains.gibddyola.ui.news.list.recyclerView.VkNewsRVAdapter
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class VkNewsFragment : Fragment(), VkNewsContract.View {

    private var _binding: FragmentVkNewsBinding? = null
    private val binding get() = _binding!!

    private val newsList = mutableListOf<VkNewsEntity.Response.Item>()

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
        viewModel.vkNews.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                newsList.addAll(it)
                adapter.setData(newsList)
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
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAdapterClicker() {
        adapter.setOnItemClickListener(object : VkNewsRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_activity_container, VkNewsDetailsFragment.newInstance(
                        newsList[position]
                    ))
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}