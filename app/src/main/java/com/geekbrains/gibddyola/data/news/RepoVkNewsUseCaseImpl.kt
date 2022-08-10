package com.geekbrains.gibddyola.data.news

import com.geekbrains.gibddyola.data.news.api.VkApi
import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import com.geekbrains.gibddyola.domain.news.RepoVkNewsUseCase
import kotlinx.coroutines.Deferred


class RepoVkNewsUseCaseImpl(private val api: VkApi) : RepoVkNewsUseCase {

    override suspend fun receiveNewsAsync(): Deferred<VkNewsEntity> {
     return api.listWallNewsAsync(
         VkData.COUNT,
         VkData.FILTER,
         VkData.SDK_VER,
         VkData.VK_TOKEN
     )
    }

}