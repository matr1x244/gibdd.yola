package com.geekbrains.gibddyola.data.news.api

import com.geekbrains.gibddyola.data.news.entity.VkNewsEntity
import kotlinx.coroutines.Deferred

import retrofit2.http.GET

interface VkApi {
    @GET("news")
    fun listWallNewsAsync(): Deferred<List<VkNewsEntity.Response.Item>>
}