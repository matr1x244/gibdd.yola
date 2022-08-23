package com.geekbrains.gibddyola.utils.updates

object UpdateData {
    private const val downloadApkUrl = "https://гибдд12.рф/img/photos/posters/app/new_app.apk"
    private const val versionTextUrl = "https://гибдд12.рф/img/photos/posters/app/app_version.txt"
    private const val fileName = "avarkomNew.apk"

    fun apkUrl() = downloadApkUrl
    fun versionUrl() = versionTextUrl
    fun fileName() = fileName
}