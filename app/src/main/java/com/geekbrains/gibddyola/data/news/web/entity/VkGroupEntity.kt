package com.geekbrains.gibddyola.data.news.web.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VkGroupEntity(
    val response: List<Response>
) : Parcelable {
    @Parcelize
    data class Response(
        val id: Int,
        val description: String,
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
    ) : Parcelable
}