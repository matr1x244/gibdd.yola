package com.geekbrains.gibddyola.utils.updates

import java.io.File

class IsApkExist(downloadPath: String) {
    private val apkFile = File("$downloadPath/${UpdateData.fileName()}")

    fun start() = apkFile.exists()
}