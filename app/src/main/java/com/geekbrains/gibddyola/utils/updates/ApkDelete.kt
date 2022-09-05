package com.geekbrains.gibddyola.utils.updates

import android.util.Log
import java.io.File

class ApkDelete(private val downloadPath: String) {
    private val apkFile = File("$downloadPath/${UpdateData.fileName()}")

    fun run() {
        if (apkFile.exists()) {
            apkFile.delete()
            if (apkFile.exists()) {
                apkFile.canonicalFile.delete()
                if (apkFile.exists()) {
                    apkFile.deleteRecursively()
                }
            }
        } else {
            Log.e(
                "",
                "Файл в загрузках не найден $downloadPath/${UpdateData.fileName()}"
            )
        }
    }
}