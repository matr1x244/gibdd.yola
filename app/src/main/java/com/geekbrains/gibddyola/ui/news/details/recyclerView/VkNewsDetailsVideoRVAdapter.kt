package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsDetailsVideoRVAdapter :
    RecyclerView.Adapter<VkNewsDetailsVideoRVAdapter.VkNewsVideoDetailsViewHolder>() {

    private val videos = mutableListOf<VkNewsEntity.Response.Item.Attachment.Video.Image?>()

    private lateinit var mListener: OnDetailsItemClickListener

    fun setData(resultData: VkNewsEntity.Response.Item) {
        videos.clear()
        resultData.attachments?.forEach { attachment ->
            if (attachment.type == "video") {
                videos.add(attachment.video?.image?.get(2))
            }
        }
        notifyDataSetChanged()
    }

    fun setOnDetailsItemClickListener(listener: OnDetailsItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VkNewsVideoDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_details_video, parent, false)
        return VkNewsVideoDetailsViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: VkNewsVideoDetailsViewHolder, position: Int) {
        holder.bindVideo(videos[position])
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class VkNewsVideoDetailsViewHolder(
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
}

interface OnDetailsItemClickListener {
    fun onItemClick(position: Int)
}