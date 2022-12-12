package com.geekbrains.gibddyola.ui.news.list.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity
import com.geekbrains.gibddyola.domain.news.RepoVkGroupUseCase
import com.geekbrains.gibddyola.domain.news.RepoVkNewsUseCase
import com.geekbrains.gibddyola.ui.news.list.VkNewsContract
import com.geekbrains.gibddyola.utils.checkConnection.ConnectionChecker
import com.geekbrains.gibddyola.utils.checkConnection.NetworkStatus
import com.geekbrains.gibddyola.utils.flow_loading.LoadingFlowRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

class VkNewsViewModel(
    private val repoNewsUseCase: RepoVkNewsUseCase,
    private val repoGroupUseCase: RepoVkGroupUseCase,
    private val loadingFlowRepository: LoadingFlowRepository,
    private val connectionChecker: ConnectionChecker
) : VkNewsContract.ViewModel() {
    override val vkNews: MutableLiveData<List<VkNewsEntity.Response.Item>> =
        MutableLiveData<List<VkNewsEntity.Response.Item>>()
    override val groupInfo: MutableLiveData<List<VkGroupEntity.Response>> =
        MutableLiveData<List<VkGroupEntity.Response>>()
    override val inProgress: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    override val onError: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    override val isBlocked: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus

    private val _flowData = MutableLiveData<Char>()
    val flowData: LiveData<Char> = _flowData

    fun getLoadingText() {
        viewModelScope.launch {
            loadingFlowRepository.get().collectLatest {
                _flowData.postValue(it)
            }
            _flowData.value = '\u0000'
        }
    }

    override fun setNews() {
        coroutineScope.launch {
            inProgress.postValue(true)
            try {
                vkNews.postValue(repoNewsUseCase.receiveNewsAsync().await().response.items)
                groupInfo.postValue(repoGroupUseCase.receiveGroupInfoAsync().await().response)
                inProgress.postValue(false)
            } catch (e: Exception) {
                onError.postValue(e)
            }
        }
    }

    override fun blockScreen(isBlock: Boolean) {
        isBlocked.postValue(isBlock)
    }

    override fun connectionCheck() {
        coroutineScope.launch {
            connectionChecker.networkStatus.collect { status ->
                when (status) {
                    NetworkStatus.Available -> {
                        _connectionStatus.value = true
                    }
                    else -> {
                        _connectionStatus.value = false
                    }
                }
            }
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}