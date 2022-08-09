package com.geekbrains.gibddyola.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.utils.EquilateralImageView

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
        private val image: EquilateralImageView = itemView.findViewById(R.id.vk_news_item_image)
        private val text: TextView = itemView.findViewById(R.id.vk_news_item_text)

        fun bind(item: VkNewsEntity.Response.Item) {
            var imageUrl = ""
            item.attachments?.forEach { attachment ->
                if (attachment.photo?.sizes?.get(1)?.url?.isNotEmpty() == true) {
                    image.visibility = View.VISIBLE
                    imageUrl = attachment.photo.sizes[1].url
                } else {
                    image.visibility = View.GONE
                }
            }
            if (imageUrl.isNotEmpty()) {
                Glide.with(itemView)
                    .load(imageUrl)
                    .into(image)
            }
            text.text = item.text
        }
    }
}