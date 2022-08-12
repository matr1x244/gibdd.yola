package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsDetailsVideoRVAdapter :
    RecyclerView.Adapter<VkNewsVideoDetailsViewHolder>() {

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
}