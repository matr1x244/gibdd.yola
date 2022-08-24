package com.geekbrains.gibddyola.utils.updates

import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class ReceiveServerAppApk {
    private val downloadSuccess = "Файл загружен"
    private val fileNotFound = "Файл не найден"
    private val downloadError = "Ошибка загрузки"
    private val ioError = "Ошибка получения данных"

    fun downloadFile(): String {
        try {
            val url = URL(UpdateData.apkUrl())
            val fileName = UpdateData.fileName()

            url.openStream().use { inpStr ->
                BufferedInputStream(inpStr).use { buffInpStr ->
                    FileOutputStream(fileName).use { fileOutStr ->
                        val data = ByteArray(1024)
                        var count: Int
                        while (buffInpStr.read(data, 0, 1024).also {
                                count = it
                            } != -1) {
                            fileOutStr.write(data, 0, count)
                        }
                    }
                }
            }
            return downloadSuccess
        } catch (c: FileNotFoundException) {
            c.printStackTrace()
            return fileNotFound
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return downloadError
        } catch (d: IOException) {
            d.printStackTrace()
            return ioError
        }
    }
}