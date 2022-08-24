package com.geekbrains.gibddyola.utils.updates

object UpdateData {
    private const val downloadApkUrl = "https://гибдд12.рф/img/photos/posters/app/app_avarkom.apk"
    private const val versionTextUrl = "https://гибдд12.рф/img/photos/posters/app/app_version.txt"
    private const val fileName = "/storage/emulated/0/Download/app_avarkom.apk"

    fun apkUrl() = downloadApkUrl
    fun versionUrl() = versionTextUrl
    fun fileName() = fileName
}