package com.geekbrains.gibddyola.ui.news.list.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsRVAdapter : RecyclerView.Adapter<VkNewsViewHolder>() {

    private val data = mutableListOf<VkNewsEntity.Response.Item>()

    private lateinit var mListener: OnItemClickListener

    fun setData(resultData: List<VkNewsEntity.Response.Item>) {
        data.clear()
        data.addAll(resultData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_news, parent, false)
        return VkNewsViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: VkNewsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}