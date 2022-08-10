package com.geekbrains.gibddyola.domain.news

import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import kotlinx.coroutines.Deferred


interface RepoVkNewsUseCase {
    suspend fun receiveNewsAsync(): Deferred<VkNewsEntity>
}