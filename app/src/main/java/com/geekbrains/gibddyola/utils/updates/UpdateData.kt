package com.geekbrains.gibddyola.utils.updates

import android.os.Environment

object UpdateData {
    private const val downloadApkUrl = "https://гибдд12.рф/img/photos/posters/app/avarkom_new.apk"
    private const val versionTextUrl = "https://гибдд12.рф/img/photos/posters/app/app_version.txt"
    private const val fileName = "avarkom_new.apk"

    private const val downloadSuccess = "Файл загружен"
    private const val fileNotFound = "Файл не найден"
    private const val downloadError = "Ошибка загрузки"
    private const val ioError = "Ошибка получения данных"
    private const val timeOutConnection = "Соединение было сброшено"
    private const val connectionError = "Ошибка соединения"

    private const val updateDay = 23

    fun apkUrl() = downloadApkUrl
    fun versionUrl() = versionTextUrl
    fun fileName() = fileName
    fun downloadSuccess() = downloadSuccess
    fun fileNotFound() = fileNotFound
    fun downloadError() = downloadError
    fun ioError() = ioError
    fun updateDay() = updateDay
    fun timeOutConnection() = timeOutConnection
    fun connectionError() = connectionError
}