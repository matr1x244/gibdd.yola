package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsDetailsVideoRVAdapter :
    RecyclerView.Adapter<VkNewsDetailsVideoRVAdapter.VkNewsDetailsViewHolder>() {

    private val videos = mutableListOf<String>()

    fun setData(resultData: VkNewsEntity.Response.Item) {
        videos.clear()
        resultData.attachments?.forEach { attachment ->
            val tempVideoUrl =
                "https://vk.com/video${attachment.video?.owner_id}_${attachment.video?.id}"
            videos.add(tempVideoUrl)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_details_video, parent, false)
        return VkNewsDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VkNewsDetailsViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class VkNewsDetailsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val playerView: WebView =
            itemView.findViewById(R.id.vk_news_details_rv_item_video)

        fun bind(videoUrl: String) {
            playerView.loadUrl(videoUrl)
        }
    }
}