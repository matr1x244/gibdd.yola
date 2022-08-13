package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsImageDetailsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val fullImage: AppCompatImageView =
        itemView.findViewById(R.id.vk_news_details_rv_item_image)

    fun bindPhoto(image: VkNewsEntity.Response.Item.Attachment.Photo.Size?) {
        val imageUrl = image?.url.toString()

        if (imageUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(imageUrl)
                .centerInside()
                .into(fullImage)
        }
    }
}