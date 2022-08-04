package com.geekbrains.gibddyola.ui.main.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.geekbrains.gibddyola.R
import com.geekbrains.gibddyola.databinding.RecyclerItemAvarkomBinding
import com.geekbrains.gibddyola.domain.EntityAvarkom

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding = RecyclerItemAvarkomBinding.bind(itemView)

    companion object {
        fun createView(parent: ViewGroup): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_avarkom, parent, false)
            return ViewHolder(view)
        }
    }

    fun bind(item: EntityAvarkom, listener: EntityAvarkom.() -> Unit) {

        Glide.with(binding.avatar)
            .load(R.mipmap.profile_new)
            .transform(CircleCrop(), RoundedCorners(16))
            .transition(DrawableTransitionOptions.withCrossFade(2000))
            .into(binding.avatar)

        binding.itemTextName.text = item.textName
        binding.itemTextSmallAbout.text = item.textRaiting
        binding.root.setOnClickListener {
            listener.invoke(item)
        }
    }

}