package com.geekbrains.gibddyola.ui.news

import androidx.lifecycle.MutableLiveData
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.domain.news.RepoVkNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class VkNewsViewModel(
    private val repoNewsUseCase: RepoVkNewsUseCase
) : VkNewsContract.ViewModel() {
    override val vkNews: MutableLiveData<List<VkNewsEntity.Response.Item>> =
        MutableLiveData<List<VkNewsEntity.Response.Item>>()
    override val inProgress: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    override val onError: MutableLiveData<Throwable> = MutableLiveData<Throwable>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    override fun setNews() {
        inProgress.postValue(true)
        coroutineScope.launch {
            vkNews.postValue(repoNewsUseCase.receiveNewsAsync().await())
        }
        inProgress.postValue(false)
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}