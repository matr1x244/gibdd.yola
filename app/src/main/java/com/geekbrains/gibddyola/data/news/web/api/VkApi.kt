package com.geekbrains.gibddyola.data.news.web.api

import com.geekbrains.gibddyola.data.news.web.VkData
import com.geekbrains.gibddyola.data.news.web.entity.VkGroupEntity
import com.geekbrains.gibddyola.data.news.web.entity.VkNewsEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {
    @GET("${VkData.METHOD_WALL_GET}?owner_id=-${VkData.GROUP_ID}")
    fun listWallNewsAsync(
        @Query("count") count: String,
        @Query("filter") filter: String,
        @Query("v") v: String,
        @Query("access_token") access_token: String
    ): Deferred<VkNewsEntity>

    @GET("${VkData.METHOD_GROUPS_GET_BY_ID}?group_id=${VkData.GROUP_ID}")
    fun groupInfoAsync(
        @Query("fields") fields: String,
        @Query("access_token") access_token: String,
        @Query("v") v: String
    ): Deferred<VkGroupEntity>
}