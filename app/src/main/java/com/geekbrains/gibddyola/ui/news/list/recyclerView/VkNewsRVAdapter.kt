package com.geekbrains.gibddyola.ui.news.list.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity

class VkNewsRVAdapter : RecyclerView.Adapter<VkNewsViewHolder>() {

    private val data = mutableListOf<VkNewsEntity.Response.Item>()
    private val groupInfo = mutableListOf<VkGroupEntity.Response>()

    var isClickable = true

    private var mListener: OnItemClickListener? = null

    fun setData(
        resultData: List<VkNewsEntity.Response.Item>,
        groupInfoData: List<VkGroupEntity.Response>
        ) {
        data.clear()
        groupInfo.clear()
        data.addAll(resultData)
        groupInfo.addAll(groupInfoData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_news, parent, false)
        return if (isClickable) {
            VkNewsViewHolder(view, mListener)
        } else {
            VkNewsViewHolder(view, null)
        }

    }

    override fun onBindViewHolder(holder: VkNewsViewHolder, position: Int) {
        holder.bind(data[position], groupInfo[0])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}