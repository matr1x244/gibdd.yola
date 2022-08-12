package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsVideoDetailsViewHolder(
    itemView: View,
    listener: OnDetailsItemClickListener
) : RecyclerView.ViewHolder(itemView) {
    private val fullImage: AppCompatImageView =
        itemView.findViewById(R.id.vk_news_details_rv_item_video_image)

    init {
        itemView.setOnClickListener {
            listener.onItemClick(absoluteAdapterPosition)
        }
    }

    fun bindVideo(video: VkNewsEntity.Response.Item.Attachment.Video.Image?) {
        val videoUrl = video?.url.toString()

        if (videoUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(videoUrl)
                .centerInside()
                .into(fullImage)
        }
    }
}

interface OnDetailsItemClickListener {
    fun onItemClick(position: Int)
}