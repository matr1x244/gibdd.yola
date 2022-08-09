package com.geekbrains.gibddyola.data.news

object VkData {
    private const val VK_TOKEN =
        "23ef062b23ef062b23ef062b0420ff44b8223ef23ef062b411d021aedd6a8ef932cae18"
    private const val SDK_VER = "5.131"
    private const val GROUP_ID = "-189372597"
    private const val FILTER = "owner"
    private const val COUNT = "50"
    private const val API_URL = "https://api.vk.com/method/"
    private const val METHOD = "wall.get"
    const val BASE_URL = "$API_URL$METHOD?owner_id=$GROUP_ID&count=$COUNT&filter=$FILTER" +
            "&v=$SDK_VER&access_token=$VK_TOKEN"
}