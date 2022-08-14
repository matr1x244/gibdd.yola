package com.geekbrains.gibddyola.ui.main.recyclerView

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.gibddyola.domain.employee.EntityAvarkom

class AdaptersAvarkom(private val itemClick: (EntityAvarkom) -> Unit) :
    RecyclerView.Adapter<ViewHolderAvarkom>() {

    var localListData: MutableList<EntityAvarkom> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<EntityAvarkom>) {
        localListData.clear()
        localListData.addAll(newList)
        localListData.shuffle()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAvarkom {
        return ViewHolderAvarkom.createView(parent)
    }

    override fun onBindViewHolder(holder: ViewHolderAvarkom, position: Int) {
        holder.bind(getItem(position), itemClick)
    }

    private fun getItem(position: Int): EntityAvarkom {
        return localListData[position]
    }

    override fun getItemCount(): Int {
        return localListData.size
    }

}
