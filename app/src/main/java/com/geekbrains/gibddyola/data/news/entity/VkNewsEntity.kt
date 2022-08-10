package com.geekbrains.gibddyola.data.news.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VkNewsEntity(
    val response: Response
) : Parcelable {
    @Parcelize
    data class Response(
        val count: Int,
        val items: List<Item>
    ) : Parcelable {
        @Parcelize
        data class Item(
            val attachments: List<Attachment>?,
            val carousel_offset: Int?,
            val comments: Comments,
            val date: Int,
            val donut: Donut,
            val edited: Int?,
            val from_id: Int,
            val hash: String,
            val id: Int,
            val likes: Likes,
            val marked_as_ads: Int,
            val owner_id: Int,
            val post_source: PostSource,
            val post_type: String,
            val reposts: Reposts,
            val short_text_rate: Double,
            val text: String,
            val views: Views
        ) : Parcelable {
            @Parcelize
            data class Attachment(
                val photo: Photo?,
                val type: String,
                val video: Video?
            ) : Parcelable {
                @Parcelize
                data class Photo(
                    val access_key: String,
                    val album_id: Int,
                    val date: Int,
                    val has_tags: Boolean,
                    val id: Int,
                    val owner_id: Int,
                    val post_id: Int?,
                    val sizes: List<Size>,
                    val text: String,
                    val user_id: Int
                ) : Parcelable {
                    @Parcelize
                    data class Size(
                        val height: Int,
                        val type: String,
                        val url: String,
                        val width: Int
                    ) : Parcelable
                }
                @Parcelize
                data class Video(
                    val access_key: String,
                    val can_add: Int,
                    val can_add_to_faves: Int,
                    val can_comment: Int,
                    val can_like: Int,
                    val can_repost: Int,
                    val can_subscribe: Int,
                    val comments: Int,
                    val content_restricted: Int?,
                    val content_restricted_message: String?,
                    val date: Int,
                    val description: String?,
                    val duration: Int,
                    val first_frame: List<FirstFrame>?,
                    val height: Int,
                    val id: Int,
                    val image: List<Image>,
                    val owner_id: Int,
                    val title: String,
                    val track_code: String,
                    val type: String,
                    val views: Int,
                    val width: Int
                ) : Parcelable {
                    @Parcelize
                    data class FirstFrame(
                        val height: Int,
                        val url: String,
                        val width: Int
                    ) : Parcelable

                    @Parcelize
                    data class Image(
                        val height: Int,
                        val url: String,
                        val width: Int,
                        val with_padding: Int?
                    ) : Parcelable
                }
            }

            @Parcelize
            data class Comments(
                val can_post: Int,
                val count: Int,
                val groups_can_post: Boolean
            ) : Parcelable

            @Parcelize
            data class Donut(
                val is_donut: Boolean
            ) : Parcelable

            @Parcelize
            data class Likes(
                val can_like: Int,
                val can_publish: Int,
                val count: Int,
                val user_likes: Int
            ) : Parcelable

            @Parcelize
            data class PostSource(
                val platform: String,
                val type: String
            ) : Parcelable

            @Parcelize
            data class Reposts(
                val count: Int,
                val user_reposted: Int
            ) : Parcelable

            @Parcelize
            data class Views(
                val count: Int
            ) : Parcelable
        }
    }
}