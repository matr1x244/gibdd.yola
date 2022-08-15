package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity

class VkNewsDetailsImageRVAdapter :
    RecyclerView.Adapter<VkNewsImageDetailsViewHolder>() {

    private val images = mutableListOf<VkNewsEntity.Response.Item.Attachment.Photo.Size?>()

    fun setData(resultData: VkNewsEntity.Response.Item) {
        images.clear()
        resultData.attachments?.forEach { attachment ->
            if (attachment.type == "photo") {
                images.add(attachment.photo?.sizes?.get(3))
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsImageDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_details_image, parent, false)
        return VkNewsImageDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VkNewsImageDetailsViewHolder, position: Int) {
            holder.bindPhoto(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}