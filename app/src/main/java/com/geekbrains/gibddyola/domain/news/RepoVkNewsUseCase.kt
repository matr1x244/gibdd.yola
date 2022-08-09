package com.geekbrains.gibddyola.domain.news

import com.geekbrains.gibddyola.data.news.entity.VkWallNewsEntity
import kotlinx.coroutines.Deferred


interface RepoVkNewsUseCase {
    suspend fun receiveNewsAsync(): Deferred<List<VkWallNewsEntity.Response.Item>>
}