package com.geekbrains.gibddyola.ui.news.list

import androidx.lifecycle.LiveData
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity

interface VkNewsContract {
    interface View {
        fun setData()
        fun onProcessLoading()
        fun onError()
    }

    abstract class ViewModel : androidx.lifecycle.ViewModel() {
        abstract val vkNews: LiveData<List<VkNewsEntity.Response.Item>>
        abstract val inProgress: LiveData<Boolean>
        abstract val onError: LiveData<Throwable>
        abstract fun setNews()
    }
}