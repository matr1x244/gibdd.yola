package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity

class VkNewsImageDetailsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val fullImage: AppCompatImageView =
        itemView.findViewById(R.id.vk_news_details_rv_item_image)
    private val arrowBack: AppCompatImageView =
        itemView.findViewById(R.id.arrow_back)
    private val arrowForward: AppCompatImageView =
        itemView.findViewById(R.id.arrow_forward)

    fun bindPhoto(image: VkNewsEntity.Response.Item.Attachment.Photo.Size?) {
        val imageUrl = image?.url.toString()

        if (imageUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(imageUrl)
                .centerInside()
                .into(fullImage)
        }
    }

    fun arrowBackVisibility(isVisible: Boolean) {
        if (isVisible) {
            arrowBack.visibility = View.VISIBLE
        } else {
            arrowBack.visibility = View.GONE
        }
    }

    fun arrowForwardVisibility(isVisible: Boolean) {
        if (isVisible) {
            arrowForward.visibility = View.VISIBLE
        } else {
            arrowForward.visibility = View.GONE
        }
    }
}