package com.geekbrains.gibddyola.utils.updates

import android.util.Log
import java.io.File

class ApkDelete {
    private val apkFile = File("${UpdateData.downloadPath()}/${UpdateData.fileName()}")

    fun run() {
        if (apkFile.canWrite()) {
            Log.e("", "can write")
        } else {
            Log.e("", "can't write")
        }
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
                "Файл в загрузках не найден ${UpdateData.downloadPath()}/${UpdateData.fileName()}"
            )
        }
    }
}