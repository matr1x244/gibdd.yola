package com.geekbrains.gibddyola.data.news.web

import com.geekbrains.gibddyola.BuildConfig


object VkData {
    const val VK_TOKEN = BuildConfig.VK_TOKEN

    const val SDK_VER = "5.131"
    const val GROUP_ID = "189372597"
    const val FILTER = "owner"
    const val COUNT = "50"
    const val API_URL = "https://api.vk.com/method/"
    const val METHOD_WALL_GET = "wall.get"
    const val METHOD_GROUPS_GET_BY_ID = "groups.getById"
    const val FIELDS = "description"
}