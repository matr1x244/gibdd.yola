package com.geekbrains.gibddyola.domain.news

import com.geekbrains.gibddyola.data.news.entity.VkGroupEntity
import kotlinx.coroutines.Deferred

interface RepoVkGroupUseCase {
    suspend fun receiveGroupInfoAsync(): Deferred<VkGroupEntity>
}