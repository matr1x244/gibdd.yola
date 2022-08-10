package com.geekbrains.gibddyola.ui.news.list.recyclerView

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

    inner class VkNewsViewHolder(
        itemView: View,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val image: EquilateralImageView = itemView.findViewById(R.id.vk_news_item_image)
        private val text: TextView = itemView.findViewById(R.id.vk_news_item_text)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

        fun bind(item: VkNewsEntity.Response.Item) {
            var imageUrl = ""
            if (item.text.isNotBlank()) {
                if (item.attachments.isNullOrEmpty()) {
                    image.visibility = View.GONE
                } else {
                    item.attachments.forEach { attachment ->
                        if (attachment.type == "photo") {
                            if (attachment.photo?.sizes?.get(1)?.url?.isNotEmpty() == true) {
                                image.visibility = View.VISIBLE
                                imageUrl = attachment.photo.sizes[1].url
                            }
                        } else if (attachment.type == "video") {
                            if (attachment.video?.image?.get(1)?.url?.isNotEmpty() == true) {
                                image.visibility = View.VISIBLE
                                imageUrl = attachment.video.image[0].url
                            }
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
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}