package com.geekbrains.gibddyola.ui.news.details.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

class VkNewsDetailsImageRVAdapter :
    RecyclerView.Adapter<VkNewsDetailsImageRVAdapter.VkNewsPhotoDetailsViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VkNewsPhotoDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_vk_details_image, parent, false)
        return VkNewsPhotoDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VkNewsPhotoDetailsViewHolder, position: Int) {
            holder.bindPhoto(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class VkNewsPhotoDetailsViewHolder(
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
}