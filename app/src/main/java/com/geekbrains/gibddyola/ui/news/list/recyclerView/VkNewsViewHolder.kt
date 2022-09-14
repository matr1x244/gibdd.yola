package com.geekbrains.gibddyola.ui.news.list.recyclerView

import android.annotation.SuppressLint
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
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity
import com.geekbrains.gibddyola.utils.vkontakte.ConvertCounts
import com.geekbrains.gibddyola.utils.vkontakte.TimeStampToDataConverter
import com.google.android.material.chip.Chip

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

    @SuppressLint("ResourceAsColor")
    fun bind(
        item: VkNewsEntity.Response.Item,
        groupInfo: VkGroupEntity.Response
    ) {
        var imageUrl = ""
        if (item.text.isNotEmpty()) {
            if (item.attachments.isNullOrEmpty()) {
                image.visibility = View.GONE
            }
        }

        if (!item.attachments.isNullOrEmpty()) {
            item.attachments.forEach { attachment ->
                if (attachment.type == "photo") {
                    if (attachment.photo?.sizes?.get(2)?.url?.isNotEmpty() == true) {
                        image.visibility = View.VISIBLE
                        imageUrl = attachment.photo.sizes[2].url
                    }
                }
                if (attachment.type == "video") {
                    if (attachment.video?.image?.get(2)?.url?.isNotEmpty() == true) {
                        image.visibility = View.VISIBLE
                        imageUrl = attachment.video.image[2].url
                    }
                }
            }
        }

        val postDateTime = itemView.findViewById<AppCompatTextView>(R.id.vk_news_item_time)
        postDateTime.text = TimeStampToDataConverter.convert(item.date)

        val postLikes = itemView.findViewById<Chip>(R.id.like_vk)
        postLikes.text = item.likes.count.toString()

        val postViews = itemView.findViewById<Chip>(R.id.see_post_vk)
        postViews.text = ConvertCounts.convert(item.views.count)

        val groupName =
            itemView.findViewById<AppCompatTextView>(R.id.vk_news_item_tittle_group_name)
        groupName.text = groupInfo.name

        val groupPhoto = itemView.findViewById<AppCompatImageView>(R.id.vk_news_item_logo_image)
        Glide.with(itemView)
            .load(groupInfo.photo100)
            .error(R.mipmap.logo)
            .into(groupPhoto)

        if (imageUrl.isNotEmpty()) {
            Glide.with(itemView)
                .load(imageUrl)
                .error(R.mipmap.logo_small)
                .into(image)
        }

        if (item.text.isBlank() || item.text.isEmpty()) {
            return
        } else {
            if (item.text.lastIndex > 150) {
                val tempText = item.text.substring(0..150) + "...показать полностью"
                val spannableStringBuilder = SpannableStringBuilder(tempText)
                val blue = ForegroundColorSpan(R.color.light_blue_300)
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