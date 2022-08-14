package com.geekbrains.gibddyola.data.news.entity


import com.google.gson.annotations.SerializedName

data class VkGroupEntity(
    val response: List<Response>
) {
    data class Response(
        val id: Int,
        @SerializedName("is_closed")
        val isClosed: Int,
        val name: String,
        @SerializedName("photo_100")
        val photo100: String,
        @SerializedName("photo_200")
        val photo200: String,
        @SerializedName("photo_50")
        val photo50: String,
        @SerializedName("screen_name")
        val screenName: String,
        val type: String
    )
}