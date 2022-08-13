package com.geekbrains.gibddyola.ui.news.list.recyclerView

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.utils.TimeStampToDataConverter

class VkNewsViewHolder(
    itemView: View,
    listener: OnItemClickListener?
) : RecyclerView.ViewHolder(itemView) {
    private val image: AppCompatImageView = itemView.findViewById(R.id.vk_news_item_image)
    private val text: TextView = itemView.findViewById(R.id.vk_news_item_text)

    init {
        if (listener != null) {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    fun bind(item: VkNewsEntity.Response.Item) {
        var imageUrl = ""
        if (item.text.isNotEmpty()) {
            if (item.attachments.isNullOrEmpty()) {
                image.visibility = View.GONE
            }
        }

        if (!item.attachments.isNullOrEmpty()) {
            item.attachments.forEach { attachment ->
                if (attachment.type == "photo") {
                    if (attachment.photo?.sizes?.get(3)?.url?.isNotEmpty() == true) {
                        image.visibility = View.VISIBLE
                        imageUrl = attachment.photo.sizes[3].url
                    }
                }
                if (attachment.type == "video") {
                    if (attachment.video?.image?.get(1)?.url?.isNotEmpty() == true) {
                        image.visibility = View.VISIBLE
                        imageUrl = attachment.video.image[2].url
                    }
                }
            }
        }

        val postDateTime = itemView.findViewById<AppCompatTextView>(R.id.vk_news_item_time)
        postDateTime.text = TimeStampToDataConverter.convert(item.date)

        if (imageUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(imageUrl)
                .error(R.mipmap.no_image_vk_logo)
                .into(image)
        }

        if (item.text.isBlank() || item.text.isEmpty()) {
            return
        } else {
            if (item.text.lastIndex > 150) {
                val tempText = item.text.substring(0..150) + "...показать полностью"
                val spannableStringBuilder = SpannableStringBuilder(tempText)
                val blue = ForegroundColorSpan(R.color.light_blue_500)
                spannableStringBuilder.setSpan(blue, 151, 172, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                text.text = spannableStringBuilder
            } else if (item.text != "") {
                text.text = item.text
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}