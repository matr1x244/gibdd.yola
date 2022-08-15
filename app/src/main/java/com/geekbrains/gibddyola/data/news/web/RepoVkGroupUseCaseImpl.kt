package com.geekbrains.gibddyola.data.news.web

import com.geekbrains.gibddyola.data.news.web.api.VkApi
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.domain.news.RepoVkGroupUseCase
import kotlinx.coroutines.Deferred

class RepoVkGroupUseCaseImpl(private val api: VkApi) : RepoVkGroupUseCase {
    override suspend fun receiveGroupInfoAsync(): Deferred<VkGroupEntity> {
        return api.groupInfoAsync(
            VkData.FIELDS,
            VkData.VK_TOKEN,
            VkData.SDK_VER
        )
    }
}