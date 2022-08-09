package com.geekbrains.gibddyola.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsRVAdapter : RecyclerView.Adapter<VkNewsRVAdapter.VkNewsViewHolder>() {

    private val data = mutableListOf<VkNewsEntity.Response.Item>()

    fun setData(resultData: VkNewsEntity) {
        data.clear()
        data.addAll(resultData.response.items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_news, parent, false)
        return VkNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VkNewsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class VkNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: VkNewsEntity.Response.Item) {

        }
    }
}