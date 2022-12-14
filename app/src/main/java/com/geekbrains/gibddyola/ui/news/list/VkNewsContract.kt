package com.geekbrains.gibddyola.ui.news.list

import androidx.lifecycle.LiveData
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity

interface VkNewsContract {
    interface View {
        fun setData()
        fun onProcessLoading()
        fun onError()
        fun checkConnection()
    }

    abstract class ViewModel : androidx.lifecycle.ViewModel() {
        abstract val vkNews: LiveData<List<VkNewsEntity.Response.Item>>
        abstract val groupInfo: LiveData<List<VkGroupEntity.Response>>
        abstract val inProgress: LiveData<Boolean>
        abstract val onError: LiveData<Throwable>
        abstract val isBlocked: LiveData<Boolean>
        abstract fun setNews()
        abstract fun blockScreen(isBlock: Boolean)
        abstract fun connectionCheck()
    }
}