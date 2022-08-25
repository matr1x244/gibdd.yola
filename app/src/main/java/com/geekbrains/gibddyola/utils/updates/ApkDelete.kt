package com.geekbrains.gibddyola.utils.updates

import android.util.Log
import java.io.File

class ApkDelete {
    private val apkFile = File(UpdateData.fileName())

    fun run() {
        if (apkFile.exists()) {
            apkFile.delete()
            if (apkFile.exists()) {
                apkFile.canonicalFile.delete()
            }
        } else {
            Log.e("", "Файл в загрузках не найден")
        }
    }
}