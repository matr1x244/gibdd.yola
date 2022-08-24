package com.geekbrains.gibddyola.utils.updates

object UpdateData {
    private const val downloadApkUrl = "https://гибдд12.рф/img/photos/posters/app/app_new.jpg"
    private const val versionTextUrl = "https://гибдд12.рф/img/photos/posters/app/app_version.txt"
    private const val fileName = "/storage/emulated/0/Download/avarkomNew.apk"
    private const val downloadSuccess = "Файл загружен"
    private const val fileNotFound = "Файл не найден"
    private const val downloadError = "Ошибка загрузки"
    private const val ioError = "Ошибка получения данных"

    fun apkUrl() = downloadApkUrl
    fun versionUrl() = versionTextUrl
    fun fileName() = fileName
    fun downloadSuccess() = downloadSuccess
    fun fileNotFound() = fileNotFound
    fun downloadError() = downloadError
    fun ioError() = ioError
}