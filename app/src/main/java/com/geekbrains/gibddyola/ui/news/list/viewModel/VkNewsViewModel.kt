package com.geekbrains.gibddyola.ui.news.list.viewModel

import androidx.lifecycle.MutableLiveData
import com.geekbrains.gibddyola.data.news.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.domain.news.RepoVkGroupUseCase
import com.geekbrains.gibddyola.domain.news.RepoVkNewsUseCase
import com.geekbrains.gibddyola.ui.news.list.VkNewsContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class VkNewsViewModel(
    private val repoNewsUseCase: RepoVkNewsUseCase,
    private val repoGroupUseCase: RepoVkGroupUseCase
) : VkNewsContract.ViewModel() {
    override val vkNews: MutableLiveData<List<VkNewsEntity.Response.Item>> =
        MutableLiveData<List<VkNewsEntity.Response.Item>>()
    override val groupInfo: MutableLiveData<List<VkGroupEntity.Response>> =
        MutableLiveData<List<VkGroupEntity.Response>>()
    override val inProgress: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    override val onError: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    override val isBlocked: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    override fun setNews() {
        inProgress.postValue(true)
        coroutineScope.launch {
            try {
                vkNews.postValue(repoNewsUseCase.receiveNewsAsync().await().response.items)
                groupInfo.postValue(repoGroupUseCase.receiveGroupInfoAsync().await().response)
            } catch (e: Exception) {
                onError.postValue(e)
            }
        }
        inProgress.postValue(false)
    }

    override fun blockScreen(isBlock: Boolean) {
        isBlocked.postValue(isBlock)
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}