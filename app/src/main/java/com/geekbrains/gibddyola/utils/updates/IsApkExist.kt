package com.geekbrains.gibddyola.utils.updates

import java.io.File

class IsApkExist {
    private val apkFile = File("${UpdateData.downloadPath()}/${UpdateData.fileName()}")

    fun start() = apkFile.exists()
}